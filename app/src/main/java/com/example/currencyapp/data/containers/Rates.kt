package com.example.currencyapp.data.containers

import android.os.Parcel
import android.os.Parcelable

/**
 * Data class for rates
 * @author Sahil Salunke
 * @since 27/3/2022
 */
data class Rates(
    var currency: String? = null,
    var rate: Double? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency)
        parcel.writeValue(rate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rates> {
        override fun createFromParcel(parcel: Parcel): Rates {
            return Rates(parcel)
        }

        override fun newArray(size: Int): Array<Rates?> {
            return arrayOfNulls(size)
        }
    }
}

