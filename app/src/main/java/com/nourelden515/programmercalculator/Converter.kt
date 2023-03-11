package com.nourelden515.programmercalculator

import java.util.*
import kotlin.math.pow

class Converter {
    companion object {

        fun binaryToDecimal(binary: String): Double {
            val integerPart = binary.substringBefore(".")
            val fractionalPart = binary.substringAfter(".", "0")
            var decimal = 0.0
            for (i in integerPart.indices) {
                if (integerPart[i] == '1') {
                    decimal += 2.0.pow(integerPart.length - i - 1)
                }
            }
            for (i in fractionalPart.indices) {
                if (fractionalPart[i] == '1') {
                    decimal += 2.0.pow(-(i + 1))
                }
            }
            return decimal
        }

        //******************************************
        fun binaryToHexadecimal(binaryNumber: String): String {
            if (!binaryNumber.matches("[01]+(\\.[01]+)?".toRegex())) {
                throw IllegalArgumentException("Invalid binary number")
            }

            // Separate integer and fractional parts (if any)
            val parts = binaryNumber.split(".")
            val integerPart = parts[0]
            val fractionalPart = if (parts.size > 1) parts[1] else ""

            // Convert integer part to hexadecimal
            val integerHexadecimal = Integer.parseInt(integerPart, 2).toString(16)
                .uppercase(Locale.ROOT)

            // Convert fractional part to hexadecimal
            var fractionalHexadecimal = ""
            if (fractionalPart.isNotEmpty()) {
                // Pad the fractional part with zeros to the nearest multiple of 4
                val paddedFractionalPart =
                    (fractionalPart + "0000").substring(0, (fractionalPart.length + 3) / 4 * 4)
                // Convert each group of 4 bits to a hexadecimal digit
                for (i in paddedFractionalPart.indices step 4) {
                    val digit =
                        Integer.parseInt(paddedFractionalPart.substring(i, i + 4), 2).toString(16)
                            .uppercase(Locale.ROOT)
                    fractionalHexadecimal += digit
                }
            }

            // Combine integer and fractional parts (if any) to form the final hexadecimal number
            return if (fractionalHexadecimal.isEmpty()) {
                integerHexadecimal
            } else {
                "$integerHexadecimal.$fractionalHexadecimal"
            }
        }

        //******************************************
        fun binaryToOctal(binaryNumber: String): String {
            if (!binaryNumber.matches("[01]+(\\.[01]+)?".toRegex())) {
                throw IllegalArgumentException("Invalid binary number")
            }

            // Separate integer and fractional parts (if any)
            val parts = binaryNumber.split(".")
            val integerPart = parts[0]
            val fractionalPart = if (parts.size > 1) parts[1] else ""

            // Convert integer part to octal
            val integerOctal = Integer.parseInt(integerPart, 2).toString(8)

            // Convert fractional part to octal
            var fractionalOctal = ""
            if (fractionalPart.isNotEmpty()) {
                // Pad the fractional part with zeros to the nearest multiple of 3
                val paddedFractionalPart =
                    (fractionalPart + "000").substring(0, (fractionalPart.length + 2) / 3 * 3)
                // Convert each group of 3 bits to an octal digit
                for (i in paddedFractionalPart.indices step 3) {
                    val digit =
                        Integer.parseInt(paddedFractionalPart.substring(i, i + 3), 2).toString(8)
                    fractionalOctal += digit
                }
            }

            // Combine integer and fractional parts (if any) to form the final octal number
            return if (fractionalOctal.isEmpty()) {
                integerOctal
            } else {
                "$integerOctal.$fractionalOctal"
            }
        }

        //******************************************
        fun octalToBinary(octalNumber: String): String {
            if (!octalNumber.matches("[0-7]+(\\.[0-7]*)?".toRegex())) {
                throw IllegalArgumentException("Invalid octal number")
            }

            // Separate integer and fractional parts (if any)
            val parts = octalNumber.split(".")
            val integerPart = parts[0]
            val fractionalPart = if (parts.size > 1) parts[1] else ""

            // Convert integer part to binary
            val integerBinary = Integer.toBinaryString(Integer.parseInt(integerPart, 8))

            // Convert fractional part to binary
            var fractionalBinary = ""
            if (fractionalPart.isNotEmpty()) {
                var fraction = Integer.parseInt(fractionalPart, 8)
                var power = -1
                while (fraction > 0 && fractionalBinary.length < 8) {
                    if (fraction >= 1 shl -power) {
                        fractionalBinary += "1"
                        fraction -= 1 shl -power
                    } else {
                        fractionalBinary += "0"
                    }
                    power--
                }
            }

            // Combine integer and fractional parts (if any) to form the final binary number
            return if (fractionalBinary.isEmpty()) {
                integerBinary
            } else {
                "$integerBinary.$fractionalBinary"
            }
        }

        //******************************************
        fun decimalToBinary(decimalNumber: String): String {
            if (!decimalNumber.matches("[+-]?\\d+(\\.\\d+)?".toRegex())) {
                throw IllegalArgumentException("Invalid decimal number")
            }

            // Separate integer and fractional parts (if any)
            val parts = decimalNumber.split(".")
            val integerPart = parts[0]
            val fractionalPart = if (parts.size > 1) parts[1] else ""

            // Convert integer part to binary
            val integerBinary = Integer.toBinaryString(integerPart.toInt())

            // Convert fractional part to binary
            var fractionalBinary = ""
            if (fractionalPart.isNotEmpty()) {
                var fraction = Integer.parseInt(fractionalPart, 10)
                var power = -1
                for (i in fractionalPart.indices) {
                    if (fraction >= 1 / (1 shl -power)) {
                        fractionalBinary += "1"
                        fraction -= 1 / (1 shl -power)
                    } else {
                        fractionalBinary += "0"
                    }
                    power++
                }
            }

            // Combine integer and fractional parts (if any) to form the final binary number
            return if (fractionalBinary.isEmpty()) {
                integerBinary
            } else {
                "$integerBinary.$fractionalBinary"
            }
        }

        //******************************************
        fun hexToBinary(hexNumber: String): String {
            if (!hexNumber.matches("[\\da-fA-F]+(\\.[\\da-fA-F]*)?".toRegex())) {
                throw IllegalArgumentException("Invalid hexadecimal number")
            }

            // Separate integer and fractional parts (if any)
            val parts = hexNumber.split(".")
            val integerPart = parts[0]
            val fractionalPart = if (parts.size > 1) parts[1] else ""

            // Convert integer part to binary
            val integerBinary = Integer.toBinaryString(Integer.parseInt(integerPart, 16))

            // Convert fractional part to binary
            var fractionalBinary = ""
            if (fractionalPart.isNotEmpty()) {
                var fraction = 0.0
                var power = -1
                for (hexChar in fractionalPart) {
                    val value = Character.digit(hexChar, 16)
                    if (value < 0) {
                        throw IllegalArgumentException("Invalid hexadecimal number")
                    }
                    fraction += value * 16.0.pow(power)
                    power--
                }
                while (fraction > 0 && fractionalBinary.length < 32) {
                    fraction *= 2
                    if (fraction >= 1) {
                        fractionalBinary += "1"
                        fraction -= 1
                    } else {
                        fractionalBinary += "0"
                    }
                }
            }

            // Combine integer and fractional parts (if any) to form the final binary number
            return if (fractionalBinary.isEmpty()) {
                integerBinary
            } else {
                "$integerBinary.$fractionalBinary"
            }
        }
    }
}