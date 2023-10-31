package com.rangga.storyapp

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.rangga.storyapp.adapter.ListStoryAdapter
import com.rangga.storyapp.data.Result
import com.rangga.storyapp.data.paging.StoryPagingSource
import com.rangga.storyapp.data.parcel.ListStoryParcel
import com.rangga.storyapp.data.repository.StoryRepository
import com.rangga.storyapp.helper.DataDummy
import com.rangga.storyapp.helper.SessionManager
import com.rangga.storyapp.model.HomeViewModel
import com.rangga.storyapp.model.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private lateinit var viewModel:HomeViewModel

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

    }

    @Test
    fun `Ketika berhasil memuat cerita`() = runTest  {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<ListStoryParcel> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<ListStoryParcel>>()
        expectedQuote.value = data
        `when`(storyRepository.getStory()).thenReturn(expectedQuote)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualQuote: PagingData<ListStoryParcel> = homeViewModel.list.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.StoryCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

//      Memastikan data tidak null dan Mengembalikan Data
        assertNotNull(actualQuote)

//      Memastikan jumlah data sesuai dengan yang diharapkan
        assertEquals(dummyStory.size, differ.itemCount)

//      Memastikan data pertama yang dikembalikan sesuai
        val firstDataDummy = dummyStory[0]
        val firstDataActual = differ.getItem(0)
        assertEquals(firstDataDummy, firstDataActual)

    }

    @Test
    fun `Ketika tidak ada data cerita`() = runTest  {
        val dummyStory = DataDummy.generateDummyEmptyStoryEntity()
        val data: PagingData<ListStoryParcel> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<ListStoryParcel>>()
        expectedQuote.value = data
        `when`(storyRepository.getStory()).thenReturn(expectedQuote)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualQuote: PagingData<ListStoryParcel> = homeViewModel.list.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.StoryCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

//      memastikan jumlah data yang dikembalikan nol
        assertEquals(0, differ.itemCount)
    }
}


val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
