package cl.ucn.disc.dsm.atorres.earthquakemap.model;

import java.util.List;

public class QuakeInfo {

    /**
     *
     */
    public String id;

    /**
     *
     */
    public Properties properties;

    /**
     *
     */
    public Geometry geometry;

    /**
     *
     */
    public class Geometry {

        /**
         *
         */
        public List<Double> coordinates;

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            if (this.coordinates != null && !this.coordinates.isEmpty())
                return "Longitud: " + getLongitude() +
                        ", Latitud: " + getLatitude() +
                        ", Profundidad: " + getDepth() + "km";
            return "No coordinates";
        }

        /**
         *
         * @return
         */
        public Double getLongitude() {
            if (this.coordinates != null && !this.coordinates.isEmpty())
                return this.coordinates.get(0);
            return 0.0;
        }

        /**
         *
         * @return
         */
        public Double getLatitude() {
            if (this.coordinates != null && !this.coordinates.isEmpty())
                return this.coordinates.get(1);
            return 0.0;
        }

        /**
         *
         * @return
         */
        public Double getDepth() {
            if (this.coordinates != null && !this.coordinates.isEmpty())
                return this.coordinates.get(2);
            return 0.0;
        }
    }
}

