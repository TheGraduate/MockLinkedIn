package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.mocklinkedin.PostAdapter // Адаптер для списка постов
//import com.example.mocklinkedin.PostDataManager
import com.example.mocklinkedin.R

/*
class PostListFragment : Fragment() {

    */
/*fun setPostDataManager(postDataManager: PostDataManager) {
        this.postDataManager = postDataManager
    }*//*


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var postDataManager: PostDataManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        */
/*val adapter = PostAdapter() // Создайте адаптер для списка постов
        recyclerView.adapter = adapter*//*


        // Инициализация PostDataManager
        postDataManager = PostDataManager()

        adapter = PostAdapter() // Используйте инициализированный адаптер
        recyclerView.adapter = adapter

        // Наблюдатель за изменениями списка постов
        postDataManager.getPostListLiveData().observe(viewLifecycleOwner, Observer { posts ->
            adapter.submitList(posts)
        })

        // Ваш код для загрузки данных и передачи их в адаптер
       */
/* val dummyPosts = listOf(
            Post(1, "Пользователь 1", "2023-10-20", "Пример текста поста 1"),
            Post(2, "Пользователь 2", "2023-10-19", "Пример текста поста 2"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),
            Post(3, "Пользователь 3", "2023-10-18", "Пример текста поста 3"),

            // Добавьте здесь остальные посты
        )
        adapter.submitList(dummyPosts)*//*


        return view
    }
}*/
