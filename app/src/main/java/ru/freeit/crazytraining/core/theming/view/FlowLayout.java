package ru.freeit.crazytraining.core.theming.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int horizontalSpacing = 0;
    private float verticalSpacing = 0;

    private final List<Integer> heightOfRowsList = new ArrayList<>();
    private final List<Integer> childCountInRowsList = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        heightOfRowsList.clear();
        childCountInRowsList.clear();

        int measuredHeight = 0, measuredWidth = 0, childCount = getChildCount();
        int rowWidth = 0, maxChildHeightInRow = 0, childCountInRow = 0;
        final int rowSize = widthSize - getPaddingLeft() - getPaddingRight();
        final boolean allowFlow = widthMode != MeasureSpec.UNSPECIFIED;

        for (int currentChildIndex = 0; currentChildIndex < childCount; currentChildIndex++) {
            final View child = getChildAt(currentChildIndex);
            if (child.getVisibility() == GONE) {
                continue;
            }

            final LayoutParams childParams = child.getLayoutParams();
            int horizontalMargin = 0, verticalMargin = 0;
            if (childParams instanceof MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
                final MarginLayoutParams marginParams = (MarginLayoutParams) childParams;
                horizontalMargin = marginParams.leftMargin + marginParams.rightMargin;
                verticalMargin = marginParams.topMargin + marginParams.bottomMargin;
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }

            final int childWidth = child.getMeasuredWidth() + horizontalMargin;
            final int childHeight = child.getMeasuredHeight() + verticalMargin;
            if (allowFlow && rowWidth + childWidth > rowSize) {
                childCountInRowsList.add(childCountInRow);
                heightOfRowsList.add(maxChildHeightInRow);

                measuredHeight += maxChildHeightInRow;
                measuredWidth = Math.max(measuredWidth, rowWidth);

                childCountInRow = 1;
                rowWidth = childWidth + horizontalSpacing;

                maxChildHeightInRow = childHeight;
            } else {
                childCountInRow++;
                rowWidth += childWidth + horizontalSpacing;

                maxChildHeightInRow = Math.max(maxChildHeightInRow, childHeight);
            }
        }

        childCountInRowsList.add(childCountInRow);
        heightOfRowsList.add(maxChildHeightInRow);

        measuredHeight += maxChildHeightInRow + getPaddingTop() + getPaddingBottom();
        measuredWidth = Math.max(measuredWidth, rowWidth) + getPaddingStart() + getPaddingEnd();

        if (childCount > 1) {
            final int desiredHeight = (int) (measuredHeight + verticalSpacing * (childCount - 1));
            measuredHeight = heightMode == MeasureSpec.UNSPECIFIED ? desiredHeight : Math.min(heightSize, desiredHeight);
        }

        measuredWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : measuredWidth;
        measuredHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : measuredHeight;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int paddingStart = getPaddingStart();
        final int paddingTop = getPaddingTop();

        int childViewX = paddingStart;
        int childViewY = paddingTop;

        int rowCount = childCountInRowsList.size(), currentChildIndex = 0;
        for (int currentRowIndex = 0; currentRowIndex < rowCount; currentRowIndex++) {
            final int childCountInCurrentRow = childCountInRowsList.get(currentRowIndex);
            final int currentRowHeight = heightOfRowsList.get(currentRowIndex);

            for (int currentChildInRowIndex = 0; currentChildInRowIndex < childCountInCurrentRow && currentChildIndex < getChildCount(); ) {
                final View child = getChildAt(currentChildIndex++);
                if (child.getVisibility() == GONE) {
                    continue;
                } else {
                    currentChildInRowIndex++;
                }

                final LayoutParams childParams = child.getLayoutParams();
                int marginStart = 0, marginEnd = 0, marginTop = 0;
                if (childParams instanceof MarginLayoutParams) {
                    MarginLayoutParams marginParams = (MarginLayoutParams) childParams;
                    marginStart = marginParams.getMarginStart();
                    marginEnd = marginParams.getMarginEnd();
                    marginTop = marginParams.topMargin;
                }

                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();

                final int leftPosition = childViewX + marginStart;
                final int rightPosition = childViewX + marginStart + childWidth;
                final int topPosition = childViewY + marginTop;
                final int bottomPosition = topPosition + childHeight;

                child.layout(leftPosition, topPosition, rightPosition, bottomPosition);

                childViewX += childWidth + horizontalSpacing + marginStart + marginEnd;
            }
            childViewX = paddingStart;
            childViewY += currentRowHeight + verticalSpacing;
        }

    }

    public void changeHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        requestLayout();
    }

    public void changeVerticalSpacing(float rowSpacing) {
        verticalSpacing = rowSpacing;
        requestLayout();
    }

}