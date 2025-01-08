package com.example.fakeqq;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class SeachImageAdapter extends RecyclerView.Adapter<SeachImageAdapter.ImageViewHolder> {
    private List<String> imageUrls;  // 存储图片URL的列表
    private Context context;

    public SeachImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载每个项的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        loadImage(holder.imageView, imageUrl);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    // 创建一个ViewHolder类来绑定每个项的视图
    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            // 通过findViewById获取ImageView
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // 使用Volley加载图片
    private void loadImage(ImageView imageView, String url) {
        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // 图片加载成功，设置到ImageView
                        imageView.setImageBitmap(response);
                    }
                },
                0, 0, // 图片的宽度和高度，0表示不限制
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565, // 使用RGB_565来节省内存
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 加载失败时显示错误
                        Toast.makeText(context, "图片加载失败", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // 获取Volley请求队列并添加请求
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(imageRequest);
    }
}
