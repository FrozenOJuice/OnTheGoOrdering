package com.example.onthegoordering;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<CartItem> cart;
    private CartListener listener;

    public CartAdapter(ArrayList<CartItem> cart, CartListener listener) {
        this.cart = cart;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, extras, quantity, price, remove, customize;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            extras = itemView.findViewById(R.id.itemExtras);
            quantity = itemView.findViewById(R.id.itemQuantity);
            price = itemView.findViewById(R.id.itemPrice);
            remove = itemView.findViewById(R.id.btnRemove);
            customize = itemView.findViewById(R.id.btnCustomize);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem item = cart.get(position);

        holder.name.setText(item.name);
        holder.quantity.setText("Qty: " + item.quantity);
        holder.price.setText("$" + String.format("%.2f", item.getTotalPrice()));

        if (item.extras == null || item.extras.isEmpty()) {
            holder.extras.setText("No extras");
        } else {
            StringBuilder extrasText = new StringBuilder();
            for (Extra e : item.extras) {
                extrasText.append(e.name).append(", ");
            }
            holder.extras.setText(extrasText.toString());
        }

        holder.remove.setOnClickListener(v -> {
            listener.onRemove(position);
        });

        holder.customize.setOnClickListener(v -> {
            listener.onEdit(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public interface CartListener {
        void onRemove(int position);
        void onEdit(CartItem item, int position);
    }
}