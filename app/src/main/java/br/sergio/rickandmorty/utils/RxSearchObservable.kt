package br.sergio.rickandmorty.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxSearchObservable {
    val subject = PublishSubject.create<String>()

    fun fromView(editText: EditText): Observable<String> {
        editText.doAfterTextChanged {
            subject.onNext(it.toString())
        }

        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                subject.onComplete()
            } else if (keyCode == KeyEvent.KEYCODE_BACK){
                editText.clearFocus()
            }

            true
        }

        return subject
        //rx bindings
    }
}