package firebasepratice.com.firebasepracticetest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import firebasepratice.com.firebasepracticetest.Objects.BookObject;
import firebasepratice.com.firebasepracticetest.R;

public class ChildBookAdapter extends RecyclerView.Adapter<ChildBookAdapter.BookHolder> {
    private Context mContext;
    private List<BookObject> bookObjects= new ArrayList<>();

    public ChildBookAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setChildBook(BookObject bookObject) {
        bookObjects.add(bookObject);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.book_recycle_view, viewGroup, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int i) {
      Log.i("FDDF","helo"+bookObjects.get(i).getGenere());

          bookHolder.bookName.setText("" + bookObjects.get(i).getBookName());
        Glide.with(mContext).load(bookObjects.get(i).getImageUrl()).into(bookHolder.bookImage);

    }


    @Override
    public int getItemCount() {
        if (bookObjects==null) {
            return 0;
        }
        else
            return bookObjects.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        private TextView bookName;
        private ImageView bookImage;
        public BookHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.item_title);
            bookImage = itemView.findViewById(R.id.book_image);
        }
    }
}
