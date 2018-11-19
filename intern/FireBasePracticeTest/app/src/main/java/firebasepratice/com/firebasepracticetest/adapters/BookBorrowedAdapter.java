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

import firebasepratice.com.firebasepracticetest.Objects.BookEntryObject;
import firebasepratice.com.firebasepracticetest.Objects.BookObject;
import firebasepratice.com.firebasepracticetest.R;

public class BookBorrowedAdapter extends RecyclerView.Adapter<BookBorrowedAdapter.BookHolder> {
    private Context mContext;
    private List<BookEntryObject> bookObjects= new ArrayList<>();

    public BookBorrowedAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setBookEntry(BookEntryObject bookObject) {
        bookObjects.add(bookObject);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.bok_borrowed_recycle, viewGroup, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int i) {
    bookHolder.name.setText("");

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
        private TextView isbn,name,phone,bDate,rDate;
        public BookHolder(@NonNull View itemView) {
            super(itemView);
            isbn = itemView.findViewById(R.id.txt_isbn);
            name = itemView.findViewById(R.id.txt_name);
            phone = itemView.findViewById(R.id.txt_phone);
            bDate = itemView.findViewById(R.id.txt_borrowed_date);
            rDate = itemView.findViewById(R.id.txt_return_date);
        }
    }
}
