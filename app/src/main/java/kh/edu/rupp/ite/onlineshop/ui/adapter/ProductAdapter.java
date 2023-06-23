package kh.edu.rupp.ite.onlineshop.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderProductBinding;
public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {

    public ProductAdapter() {

        super(new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolderProductBinding binding = ViewHolderProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product item = getItem(position);
        holder.bind(item);

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ViewHolderProductBinding itemBinding;
        public ProductViewHolder(ViewHolderProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Product product) {
            Picasso.get().load(product.getImageUrl()).into(itemBinding.imgProduct);
            itemBinding.txtName.setText(product.getName());

            double price = product.getPrice();
            String priceText = "$" + price;
            itemBinding.txtPrice.setText(priceText);


        }
    }
}
