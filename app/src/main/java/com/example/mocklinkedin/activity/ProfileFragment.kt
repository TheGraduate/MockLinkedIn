package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentProfileBinding.inflate(inflater,
            container,
            false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработка нажатия кнопки "Сохранить"
        binding.buttonSave.setOnClickListener {
            // Получите значение из EditText и сохраните его, например, в SharedPreferences
            val newUserName = binding.editTextUserName.text.toString()

            // Примените новое имя пользователя в вашем приложении
        }
    }
}
