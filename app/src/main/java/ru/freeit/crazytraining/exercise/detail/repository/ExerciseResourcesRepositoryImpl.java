package ru.freeit.crazytraining.exercise.detail.repository;

import androidx.annotation.NonNull;

import ru.freeit.crazytraining.R;

public class ExerciseResourcesRepositoryImpl implements ExerciseResourcesRepository {

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
            0xff0b6623,
            0xff808000,
            0xff0047ab,
            0xff4682b4,
            0xff990f02,
            0xffbc544b,
            0xffffa500,
            0xffff7f50
        };
    }

}
