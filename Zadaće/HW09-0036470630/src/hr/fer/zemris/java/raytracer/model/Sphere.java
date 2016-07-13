package hr.fer.zemris.java.raytracer.model;

/**
 * Class that extends {@link GraphicalObject} and represent a 3D model of a
 * sphere. For correct coloring each {@link Sphere} is specified by its diffuse
 * and reflective components.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	/** {@link Sphere} center point */
	private final Point3D center;

	/** {@link Sphere} radius */
	private final double radius;

	/** Coefficient for diffuse component for color {@code red} */
	private final double kdr;

	/** Coefficient for diffuse component for color {@code green} */
	private final double kdg;

	/** Coefficient for diffuse component for color {@code blue} */
	private final double kdb;

	/** Coefficient for reflective component for color {@code red} */
	private final double krr;

	/** Coefficient for reflective component for color {@code green} */
	private final double krg;

	/** Coefficient for reflective component for color {@code blue} */
	private final double krb;

	/** Coefficient {@code n} for reflective component */
	private final double krn;

	/**
	 * Constructs a new {@link Sphere} object with the specified parameters.
	 * 
	 * @param center
	 *            center point
	 * @param radius
	 *            radius
	 * @param kdr
	 *            Coefficient for diffuse component for color {@code red}
	 * @param kdg
	 *            Coefficient for diffuse component for color {@code green}
	 * @param kdb
	 *            Coefficient for diffuse component for color {@code blue}
	 * @param krr
	 *            Coefficient for reflective component for color {@code red}
	 * @param krg
	 *            Coefficient for reflective component for color {@code green}
	 * @param krb
	 *            Coefficient for reflective component for color {@code blue}
	 * @param krn
	 *            Coefficient {@code n} for reflective component
	 */
	public Sphere(final Point3D center, final double radius, final double kdr, final double kdg, final double kdb,
			final double krr, final double krg, final double krb, final double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(final Ray ray) {
		final Point3D distance = ray.start.sub(center);
		final double scalarProduct = -ray.direction.scalarProduct(distance);
		final double distanceNorm = distance.norm() * distance.norm();
		final double squareRadius = radius * radius;
		final double discriminant = Math.sqrt(scalarProduct * scalarProduct + squareRadius - distanceNorm);
		boolean outer = true;
		double returnValue = 0.0;

		if (scalarProduct - discriminant > 0.0) {
			returnValue = scalarProduct - discriminant;

		} else if (scalarProduct + discriminant > 0.0) {
			returnValue = scalarProduct + discriminant;
			outer = false;
		} else {
			return null;
		}

		final Point3D intersection = ray.start.add(ray.direction.scalarMultiply(returnValue));

		return new RayIntersection(intersection, returnValue, outer) {

			@Override
			public double getKdb() {
				return kdb;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public Point3D getNormal() {
				return intersection.sub(center).normalize();
			}
		};
	}

}
