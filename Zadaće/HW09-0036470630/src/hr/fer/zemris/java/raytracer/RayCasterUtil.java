package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Helper class containing the necessary calculations required for the
 * Ray-tracing algorithm including color determination and
 * {@link RayIntersection} finding.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RayCasterUtil {

	/**
	 * Ambient color component
	 */
	private static final short START_COLOR = 15;

	/**
	 * Threshold to determine correct light source
	 */
	private static final double DISTANCE_THRESHOLD = 10E-9;

	/**
	 * Determines the right color for a pixel which depends on the
	 * {@link LightSource}s and other objects in the {@link Scene}. If the ray
	 * from a {@link LightSource} intersects with a pixel then the pixels color
	 * is added up with its reflective and diffusive components.
	 * 
	 * @param scene
	 *            {@link Scene} object in which all {@link GraphicalObject}s and
	 *            {@link LightSource}s are contained
	 * @param closestIntersection
	 *            closest {@link RayIntersection}
	 * @return RGB color values for a pixel
	 */
	private static short[] determineColorFor(final Scene scene, final RayIntersection closestIntersection) {
		// red, green, blue
		final short[] colors = new short[] { START_COLOR, START_COLOR, START_COLOR };
		final List<LightSource> lightSources = scene.getLights();

		for (final LightSource lightSource : lightSources) {
			final Ray ray = Ray.fromPoints(lightSource.getPoint(), closestIntersection.getPoint());
			final RayIntersection intersection = getClosestIntersection(scene, ray);

			if (intersection == null || getDistanceLightSourceIntersection(closestIntersection, lightSource)
					- getDistanceLightSourceIntersection(intersection, lightSource) > DISTANCE_THRESHOLD) {

				continue;

			} else {
				double cosine = -ray.direction.scalarProduct(closestIntersection.getNormal());
				cosine = cosine > 0 ? cosine : 0;

				colors[0] += closestIntersection.getKdr() * lightSource.getR() * cosine;
				colors[1] += closestIntersection.getKdg() * lightSource.getG() * cosine;
				colors[2] += closestIntersection.getKdb() * lightSource.getB() * cosine;

				final Point3D lightSourceVector = ray.direction.normalize();
				final Point3D reflectionVector = getReflectionVector(lightSourceVector, closestIntersection);

				final Point3D view = ray.start.sub(closestIntersection.getPoint()).normalize();

				double reflectionCosine = view.scalarProduct(reflectionVector);
				reflectionCosine = reflectionCosine < 0 ? 0 : reflectionCosine;

				colors[0] += closestIntersection.getKrr() * lightSource.getR()
						* Math.pow(reflectionCosine, closestIntersection.getKrn());
				colors[1] += closestIntersection.getKrg() * lightSource.getG()
						* Math.pow(reflectionCosine, closestIntersection.getKrn());
				colors[2] += closestIntersection.getKrb() * lightSource.getB()
						* Math.pow(reflectionCosine, closestIntersection.getKrn());
			}
		}

		return colors;
	}

	/**
	 * Finds the closest {@link RayIntersection} between the specified
	 * {@link Ray} object and a {@link GraphicalObject} contained in the
	 * specified {@link Scene}. If such {@link RayIntersection} isn't found
	 * {@code null} is returned.
	 * 
	 * @param scene
	 *            {@link Scene} object in which all {@link GraphicalObject}s are
	 *            contained
	 * @param ray
	 *            {@link Ray} object representing an light ray
	 * @return closest {@link RayIntersection} for the provided {@link Ray} if
	 *         one is found
	 */
	private static RayIntersection getClosestIntersection(final Scene scene, final Ray ray) {
		final List<GraphicalObject> sceneObjects = scene.getObjects();
		RayIntersection closestIntersection = null;
		double minimumDistance = Double.MAX_VALUE;

		for (final GraphicalObject graphicalObject : sceneObjects) {
			if (graphicalObject != null) {
				final RayIntersection intersection = graphicalObject.findClosestRayIntersection(ray);

				if (intersection != null) {
					final double distance = intersection.getDistance();
					if (distance < minimumDistance) {
						minimumDistance = distance;
						closestIntersection = intersection;
					}
				}

			}
		}

		return closestIntersection;
	}

	/**
	 * Returns the absolute distance between a {@link LightSource} and a
	 * {@link RayIntersection}.
	 * 
	 * @param closestIntersection
	 *            intersection between a object and a ray
	 * @param lightSource
	 *            source of light
	 * @return absolute distance between a {@link LightSource} and a
	 *         {@link RayIntersection}
	 */
	private static double getDistanceLightSourceIntersection(final RayIntersection closestIntersection,
			final LightSource lightSource) {
		return Math.abs(closestIntersection.getPoint().sub(lightSource.getPoint()).norm());
	}

	/**
	 * Returns the reflection vector used for reflective color calculation.
	 * 
	 * @param lightSourceVector
	 *            source of light vector
	 * @param intersection
	 *            intersection between a object and a ray
	 * @return reflection vector
	 */
	private static Point3D getReflectionVector(final Point3D lightSourceVector, final RayIntersection intersection) {
		final Point3D normal = intersection.getNormal();
		return lightSourceVector.sub(normal.scalarMultiply(lightSourceVector.scalarProduct(normal) * 2));
	}

	/**
	 * Determines the color for a pixel based on the fact if there is a
	 * {@link RayIntersection} between a {@link GraphicalObject} contained in
	 * the provided {@link Scene} and the provided {@link Ray}, if there is one
	 * then the pixel coloring is determined with the Phong model, if there
	 * isn't one then the pixel color is black.
	 * 
	 * @param scene
	 *            {@link Scene} object in which all {@link GraphicalObject}s are
	 *            contained
	 * @param ray
	 *            {@link Ray} object representing an light ray
	 * @param rgb
	 *            RGB color values for a pixel
	 */
	public static void tracer(final Scene scene, final Ray ray, final short[] rgb) {
		final RayIntersection closestIntersection = getClosestIntersection(scene, ray);
		if (closestIntersection != null) {
			final short[] colors = determineColorFor(scene, closestIntersection);

			for (int i = 0; i < colors.length; i++) {
				rgb[i] = colors[i];
			}

		} else {
			rgb[0] = rgb[1] = rgb[2] = 0;
		}
	}

}
