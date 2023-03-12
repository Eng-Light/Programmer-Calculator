package com.nourelden515.programmercalculator

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nourelden515.programmercalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var nSystem: NumberSystem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setDefaultSystem()
        setListeners()
        setObservers()
        addTextWatcher()
    }

    private fun setObservers() {
        viewModel.exceptionLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        binding.buttonConvert.setOnClickListener {
            when (nSystem) {
                NumberSystem.DECIMAL -> {
                    viewModel.convertFromDecimal()
                }
                NumberSystem.BINARY -> {
                    viewModel.convertFromBinary()
                }
                NumberSystem.OCTA -> {
                    viewModel.convertFromOcta()
                }
                NumberSystem.HEXA -> {
                    viewModel.convertFromHexa()
                }
            }
            resetFocus()
        }

        binding.buttonClear.setOnClickListener {
            clearInput()
        }
    }

    private fun addTextWatcher() {
        binding.editTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clear()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called whenever the text is changed.
                // Put your code here to be executed when the user types in the EditText.
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text is changed.
            }
        })
    }

    private fun setDefaultSystem() {
        disableButtons()
        nSystem = NumberSystem.DECIMAL
        resetDecimalButton()
        hideDecimal()
    }

    fun onClickView(v: View) {
        clearInput()
        resetViews(v)
    }

    private fun resetViews(v: View) {
        when (v) {
            binding.btnDec -> {
                nSystem = NumberSystem.DECIMAL
                disableButtons()
                resetDecimalButton()
                hideDecimal()
            }
            binding.btnBin -> {
                nSystem = NumberSystem.BINARY
                disableButtons()
                resetBinaryButton()
                hideBinary()
            }
            binding.btnOct -> {
                nSystem = NumberSystem.OCTA
                disableButtons()
                resetOctalButton()
                hideOctal()
            }
            binding.btnHex -> {
                nSystem = NumberSystem.HEXA
                disableButtons()
                resetHexaButton()
                hideHexa()
            }
        }
    }

    private fun resetDecimalButton() {
        binding.btnDec.isChecked = true
        binding.editTextInput.hint = this.getString(R.string.decimal_number)
        binding.editTextInput.inputType = InputType.TYPE_CLASS_NUMBER
        binding.editTextInput.keyListener = DigitsKeyListener.getInstance("0123456789.")
    }

    private fun resetBinaryButton() {
        binding.btnBin.isChecked = true
        binding.editTextInput.hint = this.getString(R.string.binary_number)
        binding.editTextInput.inputType = InputType.TYPE_CLASS_NUMBER
        binding.editTextInput.keyListener = DigitsKeyListener.getInstance("10.")
    }

    private fun resetOctalButton() {
        binding.btnOct.isChecked = true
        binding.editTextInput.hint = this.getString(R.string.octa_number)
        binding.editTextInput.inputType = InputType.TYPE_CLASS_NUMBER
        binding.editTextInput.keyListener = DigitsKeyListener.getInstance("01234567.")
    }

    private fun resetHexaButton() {
        binding.btnHex.isChecked = true
        binding.editTextInput.hint = this.getString(R.string.hexa_number)
        binding.editTextInput.keyListener = DigitsKeyListener.getInstance("0123456789AaBbCcDdEeFf.")
        binding.editTextInput.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    private fun disableButtons() {
        binding.btnBin.isChecked = false
        binding.btnOct.isChecked = false
        binding.btnDec.isChecked = false
        binding.btnHex.isChecked = false
    }

    private fun clear() {
        viewModel.clearAllData()
    }

    private fun clearInput() {
        binding.editTextInput.text.clear()
    }

    private fun resetFocus() {
        binding.editTextInput.clearFocus()
        // close the soft keyboard
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.editTextInput.windowToken, 0)
    }

    private fun hideBinary() {
        binding.tvBinary.visibility = View.GONE
        binding.tvBinaryText.visibility = View.GONE

        binding.tvDecimal.visibility = View.VISIBLE
        binding.tvDecimalText.visibility = View.VISIBLE

        binding.octalDividor.visibility = View.VISIBLE
        binding.decimalDividor.visibility = View.GONE

        binding.tvOctal.visibility = View.VISIBLE
        binding.tvOctalText.visibility = View.VISIBLE

        binding.tvHexa.visibility = View.VISIBLE
        binding.tvHexaText.visibility = View.VISIBLE
    }

    private fun hideDecimal() {
        binding.tvBinary.visibility = View.VISIBLE
        binding.tvBinaryText.visibility = View.VISIBLE

        binding.tvDecimal.visibility = View.GONE
        binding.tvDecimalText.visibility = View.GONE

        binding.decimalDividor.visibility = View.GONE
        binding.octalDividor.visibility = View.VISIBLE

        binding.tvOctal.visibility = View.VISIBLE
        binding.tvOctalText.visibility = View.VISIBLE

        binding.tvHexa.visibility = View.VISIBLE
        binding.tvHexaText.visibility = View.VISIBLE
    }

    private fun hideOctal() {
        binding.tvBinary.visibility = View.VISIBLE
        binding.tvBinaryText.visibility = View.VISIBLE

        binding.tvDecimal.visibility = View.VISIBLE
        binding.tvDecimalText.visibility = View.VISIBLE

        binding.tvOctal.visibility = View.GONE
        binding.tvOctalText.visibility = View.GONE
        binding.octalDividor.visibility = View.GONE
        binding.decimalDividor.visibility = View.VISIBLE

        binding.tvHexa.visibility = View.VISIBLE
        binding.tvHexaText.visibility = View.VISIBLE
    }

    private fun hideHexa() {
        binding.tvBinary.visibility = View.VISIBLE
        binding.tvBinaryText.visibility = View.VISIBLE

        binding.tvDecimal.visibility = View.VISIBLE
        binding.tvDecimalText.visibility = View.VISIBLE

        binding.tvOctal.visibility = View.VISIBLE
        binding.tvOctalText.visibility = View.VISIBLE

        binding.decimalDividor.visibility = View.VISIBLE

        binding.tvHexa.visibility = View.GONE
        binding.tvHexaText.visibility = View.GONE
    }
}