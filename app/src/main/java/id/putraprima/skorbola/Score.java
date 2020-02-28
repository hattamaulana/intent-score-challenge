package id.putraprima.skorbola;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    private String name;
    private int minute;

    public Score() {
    }

    public Score(String name, int minute) {
        this.name = name;
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.minute);
    }

    protected Score(Parcel in) {
        this.name = in.readString();
        this.minute = in.readInt();
    }

    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel source) {
            return new Score(source);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
