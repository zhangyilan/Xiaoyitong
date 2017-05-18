package maplabeing.entity;

/**
 * Created by renba on 2017/5/3.
 */

public class Parkentity {
    public  Parkentity() {

    }

    public Parkentity(int isNull, String PlateNumber, String ParkTime) {
        this.isNull = isNull;
        this.PlateNumber = PlateNumber;
        this.ParkTime = ParkTime;
    }

    private int isNull;

    private String PlateNumber;
    private String ParkTime;

    public int getIsNull() {
        return isNull;
    }

    public void setIsNull(int isNull) {
        this.isNull = isNull;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public String getParkTime() {
        return ParkTime;
    }

    public void setParkTime(String parkTime) {
        ParkTime = parkTime;
    }

}
