
package kh.edu.rupp.ite.onlineshop.ui.fragment;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import kh.edu.rupp.ite.onlineshop.CircleTransform;
import kh.edu.rupp.ite.onlineshop.api.model.Profile;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import kh.edu.rupp.ite.onlineshop.databinding.FragmentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchUserProfileData();
    }

    private void fetchUserProfileData() {

        // create retrofit client
        Retrofit httpClient = new Retrofit.Builder()
                .baseUrl("https://ferupp.s3.ap-southeast-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create service object
        ApiService apiService = httpClient.create(ApiService.class);
        Call<Profile> task = apiService.getProfile();
        task.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                // check response code
                if (response.code() != 200) {
                    Toast.makeText(getContext(), "Error. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // get response data
                Profile profile = response.body();

                // update UI
                binding.txtFullName.setText(profile.getFirst_name() + " " + profile.getLast_name());
                binding.txtEmail.setText(profile.getEmail());
                binding.edtEmail.setText(profile.getEmail());
                binding.edtPhoneNumber.setText(profile.getPhoneNumber());
                binding.edtGender.setText(profile.getGender());
                binding.edtBirthday.setText(profile.getBirthday());
                binding.edtAddress.setText(profile.getAddress());

                // Load and display the profile image using a library like Picasso or Glide
                Picasso.get().load(profile.getImageUrl())
                            .transform(new CircleTransform()) // Apply circular transformation
                            .into(binding.imgProfile);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
