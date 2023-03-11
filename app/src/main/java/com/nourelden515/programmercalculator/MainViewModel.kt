package com.nourelden515.programmercalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val inputLiveData = MutableLiveData<String>()

    private val _octalOutputLiveData = MutableLiveData<String>()
    val octalOutputLiveData: LiveData<String> = _octalOutputLiveData

    private val _binaryOutputLiveData = MutableLiveData<String>()
    val binaryOutputLiveData: LiveData<String> = _binaryOutputLiveData

    private val _decimalOutputLiveData = MutableLiveData<String>()
    val decimalOutputLiveData: LiveData<String> = _decimalOutputLiveData

    private val _hexaOutputLiveData = MutableLiveData<String>()
    val hexaOutputLiveData: LiveData<String> = _hexaOutputLiveData

    val exceptionLiveData = MutableLiveData<String>()

    fun convertFromDecimal() {
        var value = inputLiveData.value
        try {
            value = value?.let { Converter.decimalToBinary(it) }
            _binaryOutputLiveData.value = value
            fromBinary(value)
        } catch (e: java.lang.Exception) {
            exceptionLiveData.value = e.message.toString()
        }
    }

    fun convertFromBinary() {
        val value = inputLiveData.value
        _binaryOutputLiveData.value = value
        fromBinary(value)
    }

    fun convertFromOcta() {
        var value = inputLiveData.value
        try {
            value = value?.let { Converter.octalToBinary(it) }
            _binaryOutputLiveData.value = value
            fromBinary(value)
        } catch (e: java.lang.Exception) {
            exceptionLiveData.value = e.message.toString()
        }
    }

    fun convertFromHexa() {
        var value = inputLiveData.value
        try {
            value = value?.let { Converter.hexToBinary(it) }
            _binaryOutputLiveData.value = value
            fromBinary(value)
        } catch (e: java.lang.Exception) {
            exceptionLiveData.value = e.message.toString()
        }
    }

    fun clearAllData() {
        _decimalOutputLiveData.value = ""
        _binaryOutputLiveData.value = ""
        _octalOutputLiveData.value = ""
        _hexaOutputLiveData.value = ""
    }

    private fun fromBinary(value: String?) {
        try {
            _decimalOutputLiveData.value = value?.let { Converter.binaryToDecimal(it) }.toString()
            _hexaOutputLiveData.value = value?.let { Converter.binaryToHexadecimal(it) }
            _octalOutputLiveData.value = value?.let { Converter.binaryToOctal(it) }
        } catch (e: java.lang.Exception) {
            exceptionLiveData.value = e.message.toString()
        }
    }

}