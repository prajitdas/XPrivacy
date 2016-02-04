package biz.bokhorst.xprivacy;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class PUsage implements Parcelable {
	public static final Parcelable.Creator<PUsage> CREATOR = new Parcelable.Creator<PUsage>() {
		public PUsage createFromParcel(Parcel in) {
			return new PUsage(in);
		}

		public PUsage[] newArray(int size) {
			return new PUsage[size];
		}
	};

	private int uid;

	private String restrictionName;

	private String methodName;

	private boolean restricted;

	private String extra;

	private String value;

	private long time;

	public PUsage() {
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

	private PUsage(Parcel in) {
		readFromParcel(in);
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

	@Override
	public int describeContents() {
		return 0;
	}
	public String getExtra() {
		return extra;
	}
	public String getMethodName() {
		return methodName;
	}
	public String getRestrictionName() {
		return restrictionName;
	}
	public long getTime() {
		return time;
	}
	public int getUid() {
		return uid;
	}
	public String getValue() {
		return value;
	}

	// The extra is never needed in the result

	public boolean isRestricted() {
		return restricted;
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

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public void setRestrictionName(String restrictionName) {
		this.restrictionName = restrictionName;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	@SuppressLint("DefaultLocale")
	public String toString() {
		return String.format("%d/%s(%s;%s) %s=%srestricted%s", uid, methodName, extra, value, restrictionName,
				(restricted ? "" : "!"));
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
}
