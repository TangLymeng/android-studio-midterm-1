package kh.edu.rupp.ite.onlineshop.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import kh.edu.rupp.ite.onlineshop.databinding.FragmentProductBinding;
import kh.edu.rupp.ite.onlineshop.ui.adapter.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load list product from server (api)
        loadProductListFromServer();

    }

    private void loadProductListFromServer() {

        // create retrofit client
        Retrofit httpClient = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create service object
        ApiService apiService = httpClient.create(ApiService.class);

        // load product list from server
        Call<List<Product>> task = apiService.getProducts();
        task.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()){
                    showProductList(response.body());
                } else {
                    Toast.makeText(getContext(), "Load product list from server fail", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Load product list from server failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showProductList(List<Product> productList){

        // Create layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager );

        // Create adapter
        ProductAdapter adapter = new ProductAdapter();
        adapter.submitList(productList);
        binding.recyclerView.setAdapter(adapter);
    }
}
