package biz.bokhorst.xprivacy;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class PUsage implements Parcelable {
	public int uid;
	public String restrictionName;
	public String methodName;
	public boolean restricted;
	public String extra;
	public String value;
	public long time;

	// The extra is never needed in the result

	public PUsage() {
	}

	public PUsage(PUsage other) {
		uid = other.uid;
		restrictionName = other.restrictionName;
		methodName = other.methodName;
		restricted = other.restricted;
		extra = null;
		value = other.value;
		time = other.time;
	}

	public PUsage(int _uid, String category, String method) {
		uid = _uid;
		restrictionName = category;
		methodName = method;
		restricted = false;
		extra = null;
		value = null;
		time = 0;
	}

	public PUsage(int _uid, String category, String method, boolean _restricted) {
		uid = _uid;
		restrictionName = category;
		methodName = method;
		restricted = _restricted;
		extra = null;
		value = null;
		time = 0;
	}

	public PUsage(int _uid, String category, String method, boolean _restricted, boolean _asked) {
		uid = _uid;
		restrictionName = category;
		methodName = method;
		restricted = _restricted;
		extra = null;
		value = null;
		time = 0;
	}

	public static final Parcelable.Creator<PUsage> CREATOR = new Parcelable.Creator<PUsage>() {
		public PUsage createFromParcel(Parcel in) {
			return new PUsage(in);
		}

		public PUsage[] newArray(int size) {
			return new PUsage[size];
		}
	};

	private PUsage(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(uid);

		out.writeInt(restrictionName == null ? 1 : 0);
		if (restrictionName != null)
			out.writeString(restrictionName);

		out.writeInt(methodName == null ? 1 : 0);
		if (methodName != null)
			out.writeString(methodName);

		out.writeInt(restricted ? 1 : 0);

		out.writeInt(extra == null ? 1 : 0);
		if (extra != null)
			out.writeString(extra);

		out.writeInt(value == null ? 1 : 0);
		if (value != null)
			out.writeString(value);

		out.writeLong(time);
	}

	public void readFromParcel(Parcel in) {
		uid = in.readInt();
		restrictionName = (in.readInt() > 0 ? null : in.readString());
		methodName = (in.readInt() > 0 ? null : in.readString());
		restricted = (in.readInt() > 0 ? true : false);
		extra = (in.readInt() > 0 ? null : in.readString());
		value = (in.readInt() > 0 ? null : in.readString());
		time = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	@SuppressLint("DefaultLocale")
	public String toString() {
		return String.format("%d/%s(%s;%s) %s=%srestricted%s", uid, methodName, extra, value, restrictionName,
				(restricted ? "" : "!"));
	}
}
