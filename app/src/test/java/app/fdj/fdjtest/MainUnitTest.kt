package app.fdj.fdjtest

import app.fdj.fdjtest.presenter.MainPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class MainUnitTest {

    private lateinit var presenter: MainPresenter
    private lateinit var view: MainContract.View
    private lateinit var interactor: MainContract.Interactor

    @Before
    fun setup() {
        view = mock()
        interactor = mock()
        presenter = MainPresenter(view)
    }

    @Test
    fun queryError_viewHideLoading() {
        presenter.onQueryError()
        verify(view).hideLoading()
    }

    @Test
    fun queryError_viewShowsErrorMessage() {
        presenter.onQueryError()
        verify(view).showInfoMessage(anyString())
    }

    @After
    fun tearDown() {
    }
}
