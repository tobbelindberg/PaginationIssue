package com.paginationissue.paging;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public final class TokenProviderRestoreState<T extends Serializable> implements Parcelable {

    private final T mToken;

    public static <T extends Serializable> TokenProviderRestoreState<T> create(T token) {
        return new TokenProviderRestoreState<T>(token);
    }

    private TokenProviderRestoreState(T token) {
        mToken = token;
    }

    protected TokenProviderRestoreState(Parcel in) {
        mToken = (T) in.readSerializable();
    }

    public T token() {
        return mToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mToken);
    }

    public static final Creator<TokenProviderRestoreState> CREATOR =
            new Creator<TokenProviderRestoreState>() {
                @Override
                public TokenProviderRestoreState createFromParcel(Parcel source) {
                    return new TokenProviderRestoreState(source);
                }

                @Override
                public TokenProviderRestoreState[] newArray(int size) {
                    return new TokenProviderRestoreState[size];
                }
            };
}
