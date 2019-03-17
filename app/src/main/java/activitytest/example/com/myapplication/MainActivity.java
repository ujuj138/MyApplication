package activitytest.example.com.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView mylist;
    List<Song> list;
    MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylist = (ListView) findViewById(R.id.mylist);

        list = new ArrayList<>();

        list = MusicUtils.getmusic(this);

        MyAdapter myAdapter = new MyAdapter(this, list);
        mylist.setAdapter(myAdapter);


    }

    public void  play(String path){
        try{
            mediaPlayer.reset();

            mediaPlayer.setDataSource(path);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                public void onPrepared(MediaPlayer mediaPlayer){
                    mediaPlayer.start();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    class MyAdapter extends BaseAdapter {

        Context context;
        List<Song> list;

        public MyAdapter(MainActivity mainActivity, List<Song> list) {
            this.context = mainActivity;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Myholder myholder;

            if (view == null) {
                myholder = new Myholder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title, null);



                myholder.t_position=(TextView) view.findViewById(R.id.t_postion);
                myholder.t_song = (TextView)view.findViewById(R.id.t_song);
                myholder.t_singer = (TextView)view.findViewById(R.id.t_singer);
                myholder.t_duration = (TextView)view.findViewById(R.id.t_duration);

                view.setTag(myholder);

            } else {
                myholder = (Myholder) view.getTag();
            }

            myholder.t_song.setText(list.get(i).song.toString());
            myholder.t_singer.setText(list.get(i).singer.toString());
            String time = MusicUtils.formatTime(list.get(i).duration);

            myholder.t_duration.setText(time);
            myholder.t_position.setText(i + 1 + "");


            return view;
        }


        class Myholder {
            TextView t_position, t_song, t_singer, t_duration;
        }


    }
}
