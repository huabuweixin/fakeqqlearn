package com.example.fakeqq;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class linkviewAdapter extends RecyclerView.Adapter<linkviewAdapter.MyViewHolder> {

    private RecyclerView rmv;
    private Context context;
    private List<Communication> datasource;
    public linkviewAdapter(Context context,RecyclerView recyclerView){
        this.rmv=recyclerView;
        this.context=context;
        this.datasource=new ArrayList<>();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDatasource(List<Communication> datasource){
        this.datasource=datasource;
        notifyDataSetChanged();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,number;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
        }
    }


    @NonNull
    @Override
    public linkviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new linkviewAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.linkitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull linkviewAdapter.MyViewHolder holder, int position) {
        Communication item = datasource.get(position);
        holder.name.setText(item.getName());
        holder.number.setText(item.getNumber());
        // 添加长按事件以删除
        holder.itemView.setOnLongClickListener(v -> {
            // 删除联系人
            Uri uri = Uri.parse("content://com.example.fakeqq.provider/information/" + item.getId());
            context.getContentResolver().delete(uri, null, null);
            // 刷新 RecyclerView
            ((findlinkviews) context).refreshData();
            return true;
        });
        // 添加点击事件以更新
        holder.itemView.setOnClickListener(v -> {
            // 创建更新联系人弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("更新联系人");

            // 动态创建弹窗布局
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 40);

            // 输入姓名的编辑框
            EditText nameInput = new EditText(context);
            nameInput.setHint("更新姓名");
            nameInput.setText(item.getName()); // 显示当前姓名
            layout.addView(nameInput);

            // 输入号码的编辑框
            EditText numberInput = new EditText(context);
            numberInput.setHint("更新号码");
            numberInput.setInputType(InputType.TYPE_CLASS_PHONE); // 设置为电话号码输入类型
            numberInput.setText(item.getNumber()); // 显示当前号码
            layout.addView(numberInput);

            builder.setView(layout);

            // 确认按钮事件
            builder.setPositiveButton("确认", (dialog, which) -> {
                String updatedName = nameInput.getText().toString().trim();
                String updatedNumber = numberInput.getText().toString().trim();

                // 检查输入是否为空
                if (updatedName.isEmpty() || updatedNumber.isEmpty()) {
                    Toast.makeText(context, "姓名和号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 更新数据库
                ContentValues values = new ContentValues();
                values.put("name", updatedName);
                values.put("price", updatedNumber); // "price" 字段代表号码
                Uri uri = Uri.parse("content://com.example.fakeqq.provider/information/" + item.getId());
                int rowsUpdated = context.getContentResolver().update(uri, values, null, null);

                // 提示更新结果
                if (rowsUpdated > 0) {
                    Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                }

                // 刷新 RecyclerView
                ((findlinkviews) context).refreshData();
            });

            // 取消按钮事件
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

            // 显示弹窗
            builder.create().show();
        });

    }


    @Override
    public int getItemCount() {
        return datasource.size();
    }
}
