package ru.freeit.crazytraining.exercise.repository;

import androidx.annotation.NonNull;

import ru.freeit.crazytraining.R;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    @NonNull
    @Override
    public int[] icons() {
        return new int[] {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4,
            R.drawable.icon5,
            R.drawable.icon6,
            R.drawable.icon7,
            R.drawable.icon8
        };
    }

    @NonNull
    @Override
    public int[] colors() {
        return new int[] {
            0xffff00ff,
            0xff0047ab,
            0xff0b6623,
            0xff32cd32,
            0xffffa500
        };
    }

}
