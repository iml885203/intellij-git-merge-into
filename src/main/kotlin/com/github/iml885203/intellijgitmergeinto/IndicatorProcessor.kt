package com.github.iml885203.intellijgitmergeinto

import com.intellij.openapi.progress.ProgressIndicator

class IndicatorProcessor(private var indicator: ProgressIndicator, private var step: Int) {
    private var currentStep = 0

    init {
        this.indicator.isIndeterminate = false
    }

    private fun isLastStep() = currentStep == step

    fun nextStep(text: String) {
        currentStep++
        indicator.text = text
        updateFraction()
    }

    private fun updateFraction() {
        if (isLastStep()) {
            indicator.isIndeterminate = true
        } else {
            indicator.fraction = currentStep.toDouble() / step
        }
    }


}
