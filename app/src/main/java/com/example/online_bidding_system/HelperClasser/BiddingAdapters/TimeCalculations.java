package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeCalculations {

    //private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate endDate;
    private LocalDateTime currentTime;
    final ZoneId zid = ZoneId.of("Asia/Colombo");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimeCalculations(String endTime, String endDate) {
        //this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.endDate = LocalDate.parse(endDate);
        this.currentTime = LocalDateTime.now(zid);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calcFinalDateTime() {
        LocalDateTime finalDate = LocalDateTime.of(this.endDate , this.endTime);

        return finalDate.toString();
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime calcFinalDateTimeInLDT(LocalTime Endtime , LocalDate enddate) {
        LocalDateTime finalDate = LocalDateTime.of(enddate , Endtime);

        return finalDate;
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calcTimeDifInMIn_Hrs_Str(){

        LocalDateTime endTime = this.calcFinalDateTimeInLDT(this.endTime , this.endDate);
        long difInMinutes = ChronoUnit.MINUTES.between(currentTime , endTime);
        String hourDif = Long.toString(difInMinutes / 60);
        String minDif = Long.toString(difInMinutes % 60);
        String FormateStr = hourDif + ":" + minDif;

        LocalTime durationInFormat = LocalTime.MIN.plus(
                Duration.ofMinutes(difInMinutes));

        return durationInFormat.toString();
    };

    public long getRemTimeinMills(){
        LocalDateTime endTime = this.calcFinalDateTimeInLDT(this.endTime , this.endDate);
        long difInMills = ChronoUnit.MILLIS.between(currentTime , endTime);
        return difInMills;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime calcTimeDifInMIn_Hrs_InTime(){

        LocalDateTime endTime = this.calcFinalDateTimeInLDT(this.endTime , this.endDate);
        long difInMinutes = ChronoUnit.MINUTES.between(currentTime , endTime);
        String hourDif = Long.toString(difInMinutes / 60);
        String minDif = Long.toString(difInMinutes % 60);
        String FormateStr = hourDif + ":" + minDif;

        LocalTime durationInFormat = LocalTime.MIN.plus(
                Duration.ofMinutes(difInMinutes));

        return durationInFormat;
    };

    public boolean isDeleteable(){
        LocalDateTime endTime = this.calcFinalDateTimeInLDT(this.endTime , this.endDate);
        long difInMinutes = ChronoUnit.MINUTES.between(currentTime , endTime);
        if(difInMinutes >= 300){
            //Can be deleted
            return true;
        }
        else
            return false;
    }

    public boolean isExpired(){
        LocalDateTime endDateTime = calcFinalDateTimeInLDT(this.endTime , endDate);

        if (endDateTime.isBefore(this.currentTime) || endDateTime.isEqual(this.currentTime)) {
            //Expired
            return true;
        }
        else
            //Not Expired
            return false;
    }


    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }




}
