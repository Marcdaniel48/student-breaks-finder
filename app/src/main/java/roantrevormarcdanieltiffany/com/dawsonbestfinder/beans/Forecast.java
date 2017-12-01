package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * Forecast API response that we want to display
 *
 * Go to here: {@see https://openweathermap.org/forecast5#parameter} for more information
 * on the parameters.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class Forecast {
    private double temperature;
    private double temperatureMin;
    private double temperatureMax;
    private double pressure;
    private double seaLevel;
    private double grndLevel;
    private double humidity;
    private double temperatureKf;
    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private double cloudsAllPercentage;
    // Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    private double windSpeed;
    private double windDeg;
    // Rain volume for last 3 hours, mm
    private double rainMM3h;
    // Snow volume for last 3 hours
    private double snowVol3h;

    public Forecast() {
        this(-1,-1,-1,-1,-1,-1,-1,-1,-1,"","","",-1,-1,-1,-1,-1);
    }

    public Forecast(double temperature, double temperatureMin, double temperatureMax, double pressure, double seaLevel, double grndLevel, double humidity, double temperatureKf, int weatherId, String weatherMain, String weatherDescription, String weatherIcon, double cloudsAllPercentage, double windSpeed, double windDeg, double rainMM3h, double snowVol3h) {
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.pressure = pressure;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
        this.humidity = humidity;
        this.temperatureKf = temperatureKf;
        this.weatherId = weatherId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.cloudsAllPercentage = cloudsAllPercentage;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.rainMM3h = rainMM3h;
        this.snowVol3h = snowVol3h;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperatureKf() {
        return temperatureKf;
    }

    public void setTemperatureKf(double temperatureKf) {
        this.temperatureKf = temperatureKf;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getCloudsAllPercentage() {
        return cloudsAllPercentage;
    }

    public void setCloudsAllPercentage(double cloudsAllPercentage) {
        this.cloudsAllPercentage = cloudsAllPercentage;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    public double getRainMM3h() {
        return rainMM3h;
    }

    public void setRainMM3h(double rainMM3h) {
        this.rainMM3h = rainMM3h;
    }

    public double getSnowVol3h() {
        return snowVol3h;
    }

    public void setSnowVol3h(double snowVol3h) {
        this.snowVol3h = snowVol3h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast)) return false;

        Forecast forecast = (Forecast) o;

        if (Double.compare(forecast.getTemperature(), getTemperature()) != 0) return false;
        if (Double.compare(forecast.getTemperatureMin(), getTemperatureMin()) != 0) return false;
        if (Double.compare(forecast.getTemperatureMax(), getTemperatureMax()) != 0) return false;
        if (Double.compare(forecast.getPressure(), getPressure()) != 0) return false;
        if (Double.compare(forecast.getSeaLevel(), getSeaLevel()) != 0) return false;
        if (Double.compare(forecast.getGrndLevel(), getGrndLevel()) != 0) return false;
        if (Double.compare(forecast.getHumidity(), getHumidity()) != 0) return false;
        if (Double.compare(forecast.getTemperatureKf(), getTemperatureKf()) != 0) return false;
        if (getWeatherId() != forecast.getWeatherId()) return false;
        if (Double.compare(forecast.getCloudsAllPercentage(), getCloudsAllPercentage()) != 0)
            return false;
        if (Double.compare(forecast.getWindSpeed(), getWindSpeed()) != 0) return false;
        if (Double.compare(forecast.getWindDeg(), getWindDeg()) != 0) return false;
        if (Double.compare(forecast.getRainMM3h(), getRainMM3h()) != 0) return false;
        if (Double.compare(forecast.getSnowVol3h(), getSnowVol3h()) != 0) return false;
        if (getWeatherMain() != null ? !getWeatherMain().equals(forecast.getWeatherMain()) : forecast.getWeatherMain() != null)
            return false;
        if (getWeatherDescription() != null ? !getWeatherDescription().equals(forecast.getWeatherDescription()) : forecast.getWeatherDescription() != null)
            return false;
        return getWeatherIcon() != null ? getWeatherIcon().equals(forecast.getWeatherIcon()) : forecast.getWeatherIcon() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getTemperature());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTemperatureMin());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTemperatureMax());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPressure());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getSeaLevel());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getGrndLevel());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getHumidity());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTemperatureKf());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getWeatherId();
        result = 31 * result + (getWeatherMain() != null ? getWeatherMain().hashCode() : 0);
        result = 31 * result + (getWeatherDescription() != null ? getWeatherDescription().hashCode() : 0);
        result = 31 * result + (getWeatherIcon() != null ? getWeatherIcon().hashCode() : 0);
        temp = Double.doubleToLongBits(getCloudsAllPercentage());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getWindSpeed());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getWindDeg());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getRainMM3h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getSnowVol3h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "temperature=" + temperature +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", pressure=" + pressure +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                ", humidity=" + humidity +
                ", temperatureKf=" + temperatureKf +
                ", weatherId=" + weatherId +
                ", weatherMain='" + weatherMain + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", cloudsAllPercentage=" + cloudsAllPercentage +
                ", windSpeed=" + windSpeed +
                ", windDeg=" + windDeg +
                ", rainMM3h=" + rainMM3h +
                ", snowVol3h=" + snowVol3h +
                '}';
    }
}
