package com.example.onthegoordering;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    ArrayList<MenuItem> list;

    public MenuAdapter(ArrayList<MenuItem> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, btnAdd, description;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            name = itemView.findViewById(R.id.itemName);
            description = itemView.findViewById(R.id.itemDescription);
            price = itemView.findViewById(R.id.itemPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuItem item = list.get(position);
        holder.name.setText(item.name);
        holder.description.setText(item.description);
        holder.price.setText(String.format("$%.2f", item.price));
        holder.image.setImageResource(item.image);

        holder.btnAdd.setOnClickListener(v -> {

            CartItem cartItem = new CartItem(
                    item.name,
                    item.price,
                    1,
                    new ArrayList<>(),
                    item.extras,
                    item.image
            );

            CartManager.addItem(cartItem);

            android.widget.Toast.makeText(
                    v.getContext(),
                    "Added to cart",
                    android.widget.Toast.LENGTH_SHORT
            ).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CustomizeActivity.class);

            intent.putExtra("name", item.name);
            intent.putExtra("price", item.price);
            intent.putExtra("image", item.image);
            intent.putExtra("description", item.description);
            intent.putExtra("category", item.category);
            intent.putExtra("extras", item.extras);

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
