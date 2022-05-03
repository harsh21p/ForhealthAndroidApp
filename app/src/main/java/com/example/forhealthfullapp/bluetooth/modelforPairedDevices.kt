package com.example.forhealthfullapp.bluetooth

class ModelForPairedDevices (name: String?, address: String?){

    private var name: String
    private var address: String

    init {
        this.name = name!!
        this.address = address!!
    }
    fun getName(): String? {
        return name
    }
    fun setName(name: String?) {
        this.name = name!!
    }
    fun getAddress(): String? {
        return address
    }
    fun setAddress(address: String?) {
        this.address = address!!
    }
}