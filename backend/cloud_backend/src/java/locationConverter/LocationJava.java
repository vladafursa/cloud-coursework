/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locationConverter;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;


/**
 *
 * @author vladafursa
 */
public class LocationJava {
  private String status;
  private String match_type;
  private String input;
  @SerializedName("data")
  Data DataObject;
  ArrayList<Object> copyright = new ArrayList<Object>();


 // Getter Methods 

  public String getStatus() {
    return status;
  }

  public String getMatch_type() {
    return match_type;
  }

  public String getInput() {
    return input;
  }

  public Data getData() {
    return DataObject;
  }

 // Setter Methods 

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setMatch_type( String match_type ) {
    this.match_type = match_type;
  }

  public void setInput( String input ) {
    this.input = input;
  }

  public void setData( Data dataObject ) {
    this.DataObject = dataObject;
  }

public class Data {
  private String postcode;
  private String status;
  private String usertype;
  private float easting;
  private float northing;
  private float positional_quality_indicator;
  private String country;
  private float latitude;
  private float longitude;
  private String postcode_no_space;
  private String postcode_fixed_width_seven;
  private String postcode_fixed_width_eight;
  private String postcode_area;
  private String postcode_district;
  private String postcode_sector;
  private String outcode;
  private String incode;


 // Getter Methods 

  public String getPostcode() {
    return postcode;
  }

  public String getStatus() {
    return status;
  }

  public String getUsertype() {
    return usertype;
  }

  public float getEasting() {
    return easting;
  }

  public float getNorthing() {
    return northing;
  }

  public float getPositional_quality_indicator() {
    return positional_quality_indicator;
  }

  public String getCountry() {
    return country;
  }

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public String getPostcode_no_space() {
    return postcode_no_space;
  }

  public String getPostcode_fixed_width_seven() {
    return postcode_fixed_width_seven;
  }

  public String getPostcode_fixed_width_eight() {
    return postcode_fixed_width_eight;
  }

  public String getPostcode_area() {
    return postcode_area;
  }

  public String getPostcode_district() {
    return postcode_district;
  }

  public String getPostcode_sector() {
    return postcode_sector;
  }

  public String getOutcode() {
    return outcode;
  }

  public String getIncode() {
    return incode;
  }

 // Setter Methods 

  public void setPostcode( String postcode ) {
    this.postcode = postcode;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setUsertype( String usertype ) {
    this.usertype = usertype;
  }

  public void setEasting( float easting ) {
    this.easting = easting;
  }

  public void setNorthing( float northing ) {
    this.northing = northing;
  }

  public void setPositional_quality_indicator( float positional_quality_indicator ) {
    this.positional_quality_indicator = positional_quality_indicator;
  }

  public void setCountry( String country ) {
    this.country = country;
  }

  public void setLatitude( float latitude ) {
    this.latitude = latitude;
  }

  public void setLongitude( float longitude ) {
    this.longitude = longitude;
  }

  public void setPostcode_no_space( String postcode_no_space ) {
    this.postcode_no_space = postcode_no_space;
  }

  public void setPostcode_fixed_width_seven( String postcode_fixed_width_seven ) {
    this.postcode_fixed_width_seven = postcode_fixed_width_seven;
  }

  public void setPostcode_fixed_width_eight( String postcode_fixed_width_eight ) {
    this.postcode_fixed_width_eight = postcode_fixed_width_eight;
  }

  public void setPostcode_area( String postcode_area ) {
    this.postcode_area = postcode_area;
  }

  public void setPostcode_district( String postcode_district ) {
    this.postcode_district = postcode_district;
  }

  public void setPostcode_sector( String postcode_sector ) {
    this.postcode_sector = postcode_sector;
  }

  public void setOutcode( String outcode ) {
    this.outcode = outcode;
  }

  public void setIncode( String incode ) {
    this.incode = incode;
  }
}
}