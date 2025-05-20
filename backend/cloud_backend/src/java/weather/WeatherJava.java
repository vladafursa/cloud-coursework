/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

/**
 *
 * @author vladafursa
 */
public class WeatherJava {
    private String product;
    private String init;
    private Datasery[] dataseries;

    public String getProduct() { return product; }
    public void setProduct(String value) { this.product = value; }

    public String getInit() { return init; }
    public void setInit(String value) { this.init = value; }

    public Datasery[] getDataseries() { return dataseries; }
    public void setDataseries(Datasery[] value) { this.dataseries = value; }




public class Datasery {
    private long date;
    private String weather;
    private Temp2m temp2m;
    private long wind10m_max;

    public long getDate() { return date; }
    public void setDate(long value) { this.date = value; }

    public String getWeather() { return weather; }
    public void setWeather(String value) { this.weather = value; }

    public Temp2m getTemp2M() { return temp2m; }
    public void setTemp2M(Temp2m value) { this.temp2m = value; }

    public long getWind10MMax() { return wind10m_max; }
    public void setWind10MMax(long value) { this.wind10m_max = value; }
}




public class Temp2m {
    private long max;
    private long min;

    public long getMax() { return max; }
    public void setMax(long value) { this.max = value; }

    public long getMin() { return min; }
    public void setMin(long value) { this.min = value; }
}
}