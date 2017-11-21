package com.ldf.calendar.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ldf.calendar.Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class CalendarDate implements Parcelable {

    private int id;
    private String title;
    private boolean isAllDay;
    private Date startsAt;
    private Date endsAt;
    private int categoryId;
    private int groupdId;
    private String locationName;
    private CategoryEvent categoryEvent;

    private static final long serialVersionUID = 1L;
    public int year;
    public int month;  //1~12
    public int day;

    public CalendarDate(int year, int month, int day) {
        if (month > 12) {
            month = 1;
            year++;
        } else if (month < 1) {
            month = 12;
            year--;
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }



    public CalendarDate() {
        this.year = Utils.getYear();
        this.month = Utils.getMonth();
        this.day = Utils.getDay();
    }

    protected CalendarDate(Parcel in) {
        id = in.readInt();
        title = in.readString();
        isAllDay = in.readByte() != 0;
        categoryId = in.readInt();
        groupdId = in.readInt();
        locationName = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        startsAt = (java.util.Date) in.readSerializable();
        endsAt = (java.util.Date) in.readSerializable();
        categoryEvent = in.readParcelable(CategoryEvent.class.getClassLoader());
    }

    public static final Creator<CalendarDate> CREATOR = new Creator<CalendarDate>() {
        @Override
        public CalendarDate createFromParcel(Parcel in) {
            return new CalendarDate(in);
        }

        @Override
        public CalendarDate[] newArray(int size) {
            return new CalendarDate[size];
        }
    };

    /**
     * 通过修改当前Date对象的天数返回一个修改后的Date
     *
     * @return CalendarDate 修改后的日期
     */
    public CalendarDate modifyDay(int day) {
        int lastMonthDays = Utils.getMonthDays(this.year, this.month - 1);
        int currentMonthDays = Utils.getMonthDays(this.year, this.month);

        CalendarDate modifyDate;
        if (day > currentMonthDays) {
            modifyDate = new CalendarDate(this.year, this.month, this.day);
        } else if (day > 0) {
            modifyDate = new CalendarDate(this.year, this.month, day);
        } else if (day > 0 - lastMonthDays) {
            modifyDate = new CalendarDate(this.year, this.month - 1, lastMonthDays + day);
        } else {
            modifyDate = new CalendarDate(this.year, this.month, this.day);
        }
        return modifyDate;
    }


    public CalendarDate modifyWeek(int offset) {
        CalendarDate result = new CalendarDate();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DATE, offset * 7);
        result.setYear(c.get(Calendar.YEAR));
        result.setMonth(c.get(Calendar.MONTH) + 1);
        result.setDay(c.get(Calendar.DATE));
        return result;
    }


    public CalendarDate modifyMonth(int offset) {
        CalendarDate result = new CalendarDate();
        int addToMonth = this.month + offset;
        if (offset > 0) {
            if (addToMonth > 12) {
                result.setYear(this.year + (addToMonth - 1) / 12);
                result.setMonth(addToMonth % 12 == 0 ? 12 : addToMonth % 12);
            } else {
                result.setYear(this.year);
                result.setMonth(addToMonth);
            }
        } else {
            if (addToMonth == 0) {
                result.setYear(this.year - 1);
                result.setMonth(12);
            } else if (addToMonth < 0) {
                result.setYear(this.year + addToMonth / 12 - 1);
                int month = 12 - Math.abs(addToMonth) % 12;
                result.setMonth(month == 0 ? 12 : month);
            } else {
                result.setYear(this.year);
                result.setMonth(addToMonth);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean equals(CalendarDate date) {
        if (date == null) {
            return false;
        }
        if (this.getYear() == date.getYear()
                && this.getMonth() == date.getMonth()
                && this.getDay() == date.getDay()) {
            return true;
        }
        return false;
    }

    public CalendarDate cloneSelf() {
        return new CalendarDate(year, month, day);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public void setStartsAt(Date startsAt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startsAt);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.startsAt = startsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setGroupdId(int groupdId) {
        this.groupdId = groupdId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocationName() {
        return locationName;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setCategoryEvent(CategoryEvent categoryEvent) {
        this.categoryEvent = categoryEvent;
    }

    public CategoryEvent getCategoryEvent() {
        return categoryEvent;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeByte((byte) (isAllDay ? 1 : 0));
        parcel.writeInt(categoryId);
        parcel.writeInt(groupdId);
        parcel.writeString(locationName);
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(day);
        parcel.writeSerializable(startsAt);
        parcel.writeSerializable(endsAt);
        parcel.writeParcelable(categoryEvent,i);
    }

    public static class CategoryEvent implements Parcelable{

        long id;
        String title;
        String color;
        long groupId;

        public CategoryEvent(){}

        public CategoryEvent(long id, String title, String color, long groupId) {
            this.id = id;
            this.title = title;
            this.color = color;
            this.groupId = groupId;
        }

        protected CategoryEvent(Parcel in) {
            id = in.readLong();
            title = in.readString();
            color = in.readString();
            groupId = in.readLong();
        }

        public static final Creator<CategoryEvent> CREATOR = new Creator<CategoryEvent>() {
            @Override
            public CategoryEvent createFromParcel(Parcel in) {
                return new CategoryEvent(in);
            }

            @Override
            public CategoryEvent[] newArray(int size) {
                return new CategoryEvent[size];
            }
        };

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(title);
            parcel.writeString(color);
            parcel.writeLong(groupId);
        }
    }


}