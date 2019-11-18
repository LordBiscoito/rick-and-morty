package br.sergio.rickandmorty.runner

import android.app.Application
import android.content.Context
import br.sergio.rickandmorty.app.MyApplicationTest
import io.appflate.restmock.android.RESTMockTestRunner

class MyApplicationRunner : RESTMockTestRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, MyApplicationTest::class.java.canonicalName, context)
    }
}