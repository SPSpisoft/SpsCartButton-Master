package com.spisoft.spscartbutton;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.FontFamily;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;
import com.spisoft.spanim.Techniques;
import com.spisoft.spanim.YoYo;
import com.spisoft.spcircleview.CircleView;

public class SpCartButton extends RelativeLayout {
    private View rootView;
    private Drawable mIconNormal , mIconProgress , mIconSuccess , mIconFail , mIconInfo ;
    private String mTextNormal = "Add to cart", mTextEditCart = "Edit Cart", mTextDescription = "", mTextInventory = "No inventory", mTextMax = "Maximum", mTextMin = "Minimum";
    private String mDescNormal = "", mDescProgress = "", mDescSuccess = "", mDescFail = "", mDescInfo = "";
    private int mColorTextDefault = R.color.colorText, mColorNormal = R.color.colorNormal, mColorProgress = R.color.colorProgress,
            mColorSuccess = R.color.colorSuccess, mColorFail = R.color.colorFail, mColorInfo = R.color.colorFail;
    private View mViewBase, RlyCntAll;
    private boolean mColorSet = false, mColorDescSet = false;
    //    private int mModeStyle = 0;
    private Animation animSel, animation_blink, animation_rotate, animation_rotate_cb , animation_left_to_right , animation_right_to_left, animation_up_to_down, animation_down_to_up;
    private int CurrentMode;
    private boolean mBackOnFail = false, mBackOnInfo = false;
    private long mMiliDelay = 400, mMiliDelayInfo = 600;
    private boolean mSetFailColor = false, mSetInfoColor = false;
    private Context mContext;
    private OnInfoClickListener mInfoClickListener;
    private boolean mInfoKeyShowOnStable;
    private OnValueChangeListener mValueChangeListener;
    private int mModeStatus;
//            mModeFormat = 0;
    private ImageView mIcon, mIconE;
    private TextView mText, mTextDesc, mTxtCntCur, mTextCounterAll;
    private RelativeLayout mButtonRly, mCounterRly;
    private CircleView circleView_St, circleView_Ed, mTxtCntAll;
    private ContentLoadingProgressBar mProgress;
    private CircularProgressView mProgressStart, mProgressEnd;
    private MaterialCardView MainView;
    private YoYo.YoYoString mAnimate = null;
    private double mValueCart = -1, mValueOther = -1,  mValue = 0, mJump = 1, mMin = 1, mMax = 0;
    private boolean mAvailable;
    private TextView mTextCnt, mTextCounter;
    private OnVsClickListener mVsClickListener;
    private OnVeClickListener mVeClickListener;
    private OnStartTaskListener mStartTaskListener;
    private RelativeLayout RlyIconSha, RlyTextSha;
    private int fontFamilyId;
    private boolean JumpTask = false;
    private YoYo.YoYoString mYoYo = null;
    private YoYo.YoYoString mYoYo2 = null;
    private int mActFillColor = Color.GREEN;
    private int mActFillColorDefault = Color.DKGRAY;
    private boolean inTask = false;
    private double mInventory = 0;
    private boolean mInvAdd = false;
    private int mModel = 0;

//    private MaterialCardView cardView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SpCartButton(Context context) {
        super(context);
        initView(context, null, -1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SpCartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SpCartButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpCartButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //-------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    private void initView(final Context context, AttributeSet attrs, int defStyleAttr) {
        rootView = inflate(context, R.layout.sps_cart_button, this);

        mContext = context;
        mViewBase = rootView.findViewById(R.id.viewBase);
        mTextCnt = rootView.findViewById(R.id.vTextCnt);
        mTextCounter = rootView.findViewById(R.id.vTextCounter);

//        mIT = rootView.findViewById(R.id.vIT);
        mIcon = rootView.findViewById(R.id.vIcon);
//        vIconInfo = rootView.findViewById(R.id.vIconInfo);
        mIconE = rootView.findViewById(R.id.vIconE);
        mText = rootView.findViewById(R.id.vText);
        mText.setSelected(true);
        mTextDesc = rootView.findViewById(R.id.vTextDesc);
        mTextDesc.setSelected(true);
        mTxtCntCur = rootView.findViewById(R.id.txtCntCur);
        mTxtCntAll = rootView.findViewById(R.id.txtCntAll);
        RlyCntAll = rootView.findViewById(R.id.rlyCntAll);
        mTextCounterAll = rootView.findViewById(R.id.vTextCounterAll);

        mCounterRly = rootView.findViewById(R.id.rlyIconE);
        mButtonRly = rootView.findViewById(R.id.cButtonRly);

        RlyIconSha = rootView.findViewById(R.id.rlyIconSha);
        RlyTextSha = rootView.findViewById(R.id.rlyTextSha);

        mProgress = rootView.findViewById(R.id.vProgressHoz);
        mProgressStart = rootView.findViewById(R.id.cProgressStart);
        mProgressEnd = rootView.findViewById(R.id.cProgressEnd);
        //------------------------------------------------------------
        circleView_St = rootView.findViewById(R.id.cv_s);
        circleView_Ed = rootView.findViewById(R.id.cv_e);

//        circleView_St.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "cvs", Toast.LENGTH_SHORT).show();
//            }
//        });
//        cardView = rootView.findViewById(R.id.nnn);


        MainView = rootView.findViewById(R.id.mainView);

        MainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentMode <= 0){
                    postDelayed(resetToNormalMode, 3000);
                    RefreshModeStatus(1, 0);
                }
            }
        });

        mIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentMode == 1 || CurrentMode == 2)
                {
                    removeCallbacks(resetToNormalMode);
                    if(!JumpTask) {
                        JumpUp(mJump);
                        postDelayed(resetToNormalMode, 3000);
                    }
                    else {
                        if(mStartTaskListener != null && (getCurrentMode() == 0 || !inTask)) {
                            RefreshModeStatus(1, +1);
                            if(mValue != CheckJump(mJump)) {
                                inTask = true;
                                mStartTaskListener.onEvent(CheckJump(mJump));
                            }
//                            else
//                                RefreshValueText(false);
                        }
                    }
                }
                else {
                    mProgressStart.setIndeterminate(true);
                    mProgressStart.setIndeterminate(true);
                    YoYo.with(Techniques.Pulse)
                            .duration(300)
                            .repeat(0)
                            .onEnd(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    mProgressStart.setIndeterminate(false);
                                    if(mVeClickListener != null) mVeClickListener.onEvent();
                                }
                            }).playOn(circleView_St);
                }
            }
        });

        mIcon.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(CurrentMode == 1){
                    removeCallbacks(resetToNormalMode);
                    if(!JumpTask) {
                        JumpUp(mJump * 10);
                        postDelayed(resetToNormalMode, 3000);
                    }
                    else {
                        if(mStartTaskListener != null && (getCurrentMode() == 0 || !inTask)) {
                            RefreshModeStatus(1, +1);

                            if(mValue != CheckJump(mJump*10)) {
                                inTask = true;
                                mStartTaskListener.onEvent(CheckJump(mJump * 10));
                            }
                        }
                    }
                }
                else {
                    if(mVeClickListener != null) mVeClickListener.onEvent();
                }
                return true;
            }
        });

        mIconE.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if((CurrentMode == 1 || mModel == 1) && mValue > 0){
                    removeCallbacks(resetToNormalMode);
                    if(!JumpTask) {
                        JumpDn(mJump);
                        postDelayed(resetToNormalMode, 3000);
                    }
                    else {
                        if(mStartTaskListener != null && (getCurrentMode() == 0 || !inTask)) {
                            RefreshModeStatus(1, -1);
//                            mStartTaskListener.onEvent(-mJump);
                            if(mValue != CheckJump(-mJump)) {
                                inTask = true;
                                mStartTaskListener.onEvent(CheckJump(-mJump));
                            }
                        }
                    }
                }
                else {
                    mProgressEnd.setIndeterminate(true);
                    mProgressEnd.setIndeterminate(true);
                    YoYo.with(Techniques.Pulse)
                            .duration(300)
                            .repeat(0)
                            .onEnd(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    mProgressEnd.setIndeterminate(false);
                                    if(mVsClickListener != null) mVsClickListener.onEvent();
                                }
                            }).playOn(circleView_Ed);
                }
            }
        });

        mIconE.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(CurrentMode == 1 && mValue > 0){
                    removeCallbacks(resetToNormalMode);
                    if(!JumpTask) {
                        JumpDn(mJump * 10);
                        postDelayed(resetToNormalMode, 3000);
                    }
                    else {
                        if(mStartTaskListener != null && (getCurrentMode() == 0 || !inTask)) {
                            RefreshModeStatus(1, -1);
//                            mStartTaskListener.onEvent(-mJump*10);
                            if(mValue != CheckJump(-mJump*10)) {
                                inTask = true;
                                mStartTaskListener.onEvent(CheckJump(-mJump * 10));
                            }
                        }
                    }
                }
                else {
                    if(mVsClickListener != null) mVsClickListener.onEvent();
                }
                return true;
            }
        });

        animation_rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely);
        animation_rotate_cb = AnimationUtils.loadAnimation(context, R.anim.rotate_top_to_button);
        animation_blink = AnimationUtils.loadAnimation(context, R.anim.blink);
        animation_left_to_right = AnimationUtils.loadAnimation(context, R.anim.ltr_indefinitely);
        animation_right_to_left = AnimationUtils.loadAnimation(context, R.anim.rtl_indefinitely);
        animation_up_to_down = AnimationUtils.loadAnimation(context, R.anim.utd_indefinitely);
        animation_down_to_up = AnimationUtils.loadAnimation(context, R.anim.dtu_indefinitely);

//        vIconInfo.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mInfoClickListener != null) mInfoClickListener.onEvent();
//            }
//        });
//        mIconNormal = context.getResources().getDrawable(R.drawable.ic_account_circle_deep_orange_a700_24dp);
//        mIconProgress = context.getResources().getDrawable(R.drawable.ic_autorenew_yellow_a400_24dp);
//        mIconSuccess = context.getResources().getDrawable(R.drawable.ic_check_circle_green_a700_24dp);
//        mIconFail = context.getResources().getDrawable(R.drawable.ic_report_red_a700_24dp);
//        mText.setSelected(true);

        if(attrs != null){
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpCartButton, 0, 0);

//            fontFamilyId = typedArray.getResourceId(R.styleable.SpCartButton_android_fontFamily, 0);
//            setFont(fontFamilyId);

//            mColorSet = typedArray.getBoolean(R.styleable.SpCartButton_ColorBackSet, false);
//            mColorDescSet = typedArray.getBoolean(R.styleable.SpCartButton_ColorDescSet, false);
//            mProgress.setIndeterminate(typedArray.getBoolean(R.styleable.SpCartButton_Indeterminate, true));
            mActFillColor = typedArray.getColor(R.styleable.SpCartButton_ActFillColor, mActFillColorDefault);

            int atModeAnimation = typedArray.getInt(R.styleable.SpCartButton_AnimMode, 0);
            switch (atModeAnimation) {
                case 0:
                    animSel = animation_rotate;
                    break;
                case 1:
                    animSel = animation_left_to_right;
                    break;
                case 2:
                    animSel = animation_right_to_left;
                    break;
                case 3:
                    animSel = animation_up_to_down;
                    break;
                case 4:
                    animSel = animation_down_to_up;
                    break;
            }
            animSel.setDuration(typedArray.getInt(R.styleable.SpCartButton_AnimDuration, 1500));

//            int atModeIconPosition = typedArray.getInt(R.styleable.SpCartButton_ModeIconPosition, 0);
//            switch (atModeIconPosition){
//                case 0:
//                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LOCALE);
////                    mIT.setOrientation(LinearLayout.HORIZONTAL);
//                    break;
//                case 1:
//                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_RTL);
////                    mIT.setOrientation(LinearLayout.HORIZONTAL);
//                    break;
//                case 2:
//                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LTR);
////                    mIT.setOrientation(LinearLayout.HORIZONTAL);
//                    break;
//                case 3:
//                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LTR);
////                    mIT.setOrientation(LinearLayout.VERTICAL);
//                    break;
//                case 4:
//                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_RTL);
////                    mIT.setOrientation(LinearLayout.VERTICAL);
//                    break;
//            }

//            mModeStyle = typedArray.getInt(R.styleable.SpCartButton_ModeStyle, 0);

//            Drawable atBackground = typedArray.getDrawable(R.styleable.SpCartButton_SrcBackground);
//            if(atBackground != null) mViewBase.setBackground(atBackground);

//            Drawable atIcon = typedArray.getDrawable(R.styleable.SpCartButton_IconNormal);
//            if(atIcon != null) {
//                mIcon.setImageDrawable(atIcon);
//                mIconNormal = atIcon;
//                mIconProgress = typedArray.getDrawable(R.styleable.SpCartButton_IconProgress);
//                mIconSuccess = typedArray.getDrawable(R.styleable.SpCartButton_IconSuccess);
//                mIconFail = typedArray.getDrawable(R.styleable.SpCartButton_IconFail);
//                mIconInfo = typedArray.getDrawable(R.styleable.SpCartButton_IconInfo);
//            }
            JumpTask = typedArray.getBoolean(R.styleable.SpCartButton_JumpTask, false);

            int atSize = (int) typedArray.getDimension(R.styleable.SpCartButton_SizeHeight, 50);

            RelativeLayout.LayoutParams paramsMain = (RelativeLayout.LayoutParams) mViewBase.getLayoutParams();
            paramsMain.height = atSize;
            mViewBase.setLayoutParams(paramsMain);

            int mTextDescSize = typedArray.getDimensionPixelSize(R.styleable.SpCartButton_TextDescSize, 0);
            if(mTextDescSize == 0) mTextDescSize = (int) atSize/8;
            mTextDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mTextDescSize*1.3));
            mTxtCntCur.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextDescSize);
            mTextCounterAll.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mTextDescSize*1.3));

            int mTextSize = typedArray.getDimensionPixelSize(R.styleable.SpCartButton_android_textSize, 0);
            if(mTextSize == 0) mTextSize = (int) atSize/4;
            mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            mTextDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mTextSize*0.7));

            int mTextCntSize = typedArray.getDimensionPixelSize(R.styleable.SpCartButton_TextCntSize, 0);
            if(mTextCntSize == 0) mTextCntSize = atSize*2/5;
            mTextCnt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextCntSize);
            mTextCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mTextCntSize*0.9));

//            RelativeLayout.LayoutParams paramsBtn = (RelativeLayout.LayoutParams) mButtonRly.getLayoutParams();
//            paramsBtn.setMarginStart(atSize/2);
//            paramsBtn.setMarginEnd(atSize/2);
//            mButtonRly.setLayoutParams(paramsBtn);
//            double radius = getResources().getDimension(R.dimen.sps_lpr_sz_50);

//            int mModeView = typedArray.getInt(R.styleable.SpCartButton_ModeView, 0);
            switch (typedArray.getInt(R.styleable.SpCartButton_ModeStyle, 0)){
                case 1:
                    MainView.setShapeAppearanceModel(
                            MainView.getShapeAppearanceModel()
                                    .toBuilder()
                                    .setTopLeftCorner(CornerFamily.ROUNDED, atSize/2)
                                    .setTopRightCorner(CornerFamily.CUT, 0)
                                    .setBottomRightCorner(CornerFamily.ROUNDED, atSize/2)
                                    .setBottomLeftCorner(CornerFamily.CUT, 0)
                                    .build());
                    break;
                default:
                    MainView.setShapeAppearanceModel(
                            MainView.getShapeAppearanceModel()
                                    .toBuilder()
                                    .setTopLeftCorner(CornerFamily.ROUNDED, atSize/2)
                                    .setTopRightCorner(CornerFamily.ROUNDED, atSize/2)
                                    .setBottomRightCorner(CornerFamily.ROUNDED, atSize/2)
                                    .setBottomLeftCorner(CornerFamily.ROUNDED, atSize/2)
                                    .build());
                    break;
            }


            ViewGroup.LayoutParams params_circleView_St = RlyTextSha.getLayoutParams();
            params_circleView_St.width = atSize;
            params_circleView_St.height = atSize;
            RlyTextSha.setLayoutParams(params_circleView_St);

            ViewGroup.LayoutParams params_circleView_Ed = RlyIconSha.getLayoutParams();
            params_circleView_Ed.width = atSize;
            params_circleView_Ed.height = atSize;
            RlyIconSha.setLayoutParams(params_circleView_Ed);

            mIcon.setPadding(atSize/4, atSize/4, atSize/4, atSize/4);
            mIconE.setPadding(atSize/4, atSize/4, atSize/4, atSize/4);

            ViewGroup.LayoutParams params = mIcon.getLayoutParams();
            params.height = atSize;
            params.width = atSize;
            mIcon.setLayoutParams(params);

            ViewGroup.LayoutParams paramsE = mIconE.getLayoutParams();
            paramsE.height = atSize;
            paramsE.width = atSize;
            mIconE.setLayoutParams(paramsE);


//            int atIconPadding = (int) typedArray.getDimension(R.styleable.SpCartButton_IconPadding, atSize/4);
//            mIcon.setPadding(atIconPadding, atIconPadding, atIconPadding, atIconPadding);

            int atTextPadding = typedArray.getInt(R.styleable.SpCartButton_TextPadding, 10);
            mText.setPadding(atTextPadding, atTextPadding, atTextPadding, atTextPadding);

            mModeStatus = typedArray.getInt(R.styleable.SpCartButton_ModeStatus, 0);
//            mModeStatus = atModeStatus;
//            mModeFormat = typedArray.getInt(R.styleable.SpCartButton_ModeFormat, 0);

            mModel = typedArray.getInt(R.styleable.SpCartButton_Model, 0);
            if(mModel == 1){
                mInvAdd = true;
                mModeStatus = 1;
            }

            RefreshModeStatus(mModeStatus, 0);

            String atTextNormal = typedArray.getString(R.styleable.SpCartButton_TextAddToCart);
            if(atTextNormal != null) mTextNormal = atTextNormal;
            String atTextEditCart = typedArray.getString(R.styleable.SpCartButton_TextEditCart);
            if(atTextEditCart != null) mTextEditCart = atTextEditCart;
            String atTextMaximum = typedArray.getString(R.styleable.SpCartButton_TextMaximum);
            if(atTextMaximum != null) mTextMax = atTextMaximum;
            String atTextMinimum = typedArray.getString(R.styleable.SpCartButton_TextMinimum);
            if(atTextMinimum != null) mTextMin = atTextMinimum;
            String atTextNoInventory = typedArray.getString(R.styleable.SpCartButton_TextNoInventory);
            if(atTextNoInventory != null) mTextInventory = atTextNoInventory;
            String atTextDescription = typedArray.getString(R.styleable.SpCartButton_TextDescription);
            if(atTextDescription != null) mTextDescription = atTextDescription;

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                mColorTextDefault = typedArray.getColor(R.styleable.SpCartButton_ColorText, context.getColor(mColorTextDefault));
//                mColorNormal = typedArray.getColor(R.styleable.SpCartButton_ColorNormal, context.getColor(mColorNormal));
//                mColorProgress = typedArray.getColor(R.styleable.SpCartButton_ColorBackSet, context.getColor(mColorProgress));
//                mColorSuccess = typedArray.getColor(R.styleable.SpCartButton_ColorDescSet, context.getColor(mColorSuccess));
//                mColorFail = typedArray.getColor(R.styleable.SpCartButton_ColorFailInventory, context.getColor(mColorFail));
//            }

        }

        RefreshModeStatus(0, 0);
//        setProgress(0);
    }

    private void JumpDn(double jump) {
        mValue = mValue - jump;
        if(mValue < mMin) mValue = 0;
        RefreshModeStatus(1, -1);

        RefreshValueText(false);
    }

    private void JumpUp(double jump) {
        mValue = mValue + jump;
        if(mValue < mMin)
            mValue = mMin;
        if(mMax > 0 && mValue > mMax)
        {
            mValue = mMax;
            RefreshModeStatus(-2, 0);
        }
        else if(!JumpTask && mInventory < mValue)
        {
            mValue = mInventory;
            RefreshModeStatus(-1, 0);
        }
        else if(JumpTask && !mInvAdd)
        {
            RefreshModeStatus(-1, 0);
        }
        else
            RefreshModeStatus(1, +1);

        RefreshValueText(false);
    }

    private double CheckJump(double jump){
        double ret ;
        if(jump > 0) {
            if (mValue + jump < mMin) {
                ret = mMin;
            }
            else if (mMax > 0 && mValue + jump > mMax) {
                ret = mMax;
                RefreshModeStatus(-2, 0);
            }
            else if (!JumpTask && mInventory < mValue + jump){
                ret = mInventory;
                RefreshModeStatus(-1, 0);
            }
            else if (JumpTask && !mInvAdd) {
                ret = mValue ;
                RefreshModeStatus(-1, 0);
            }else
                ret = mValue + jump;
        }
        else {
            if(mValue + jump < mMin)
                ret = 0;
            else
                ret = mValue + jump;
            RefreshModeStatus(1, -1);
        }
        return ret;
    }

    private void RefreshValueText(boolean onStart){

        if(mYoYo != null) mYoYo.stop();
        if(mYoYo2 != null) mYoYo2.stop();

//        if(mValue == mMin) {
//            mTextDesc.setText(mTextMin);
//            mTextDesc.setVisibility(VISIBLE);
//        }
        SetStatus_Counter(1);

        if(mValueCart >= 0) {
            double mv = (int) mValueCart + mValue;
            if (mv == (int)mv)
                mTxtCntCur.setText(String.valueOf((int)mv));
            else
                mTxtCntCur.setText(String.valueOf(mv));
        }

        if(mValueOther >= 0)
        {
            RlyCntAll.setVisibility(VISIBLE);
            double mv = (int) mValueOther + mValue;
            if (mv == (int)mv) {
                mTxtCntAll.setTitleText(String.valueOf((int)mv));
                mTextCounterAll.setText(String.valueOf((int)mv));
            }else {
                mTxtCntAll.setTitleText(String.valueOf(mv));
                mTextCounterAll.setText(String.valueOf(mv));
            }
        }

        if(mValue == (int) mValue) {
            mTextCnt.setText(String.valueOf((int) mValue));
            mTextCounter.setText(String.valueOf((int) mValue));
        }
        else {
            mTextCnt.setText(String.valueOf(mValue));
            mTextCounter.setText(String.valueOf(mValue));
        }
        if(!onStart)
            if(mValueChangeListener != null) mValueChangeListener.onEvent();
            else{
                RefreshModeStatus(0, 0);
                if(!mInvAdd){
                    RefreshModeStatus(-1, 0);
//                SetStatus_Icon(1,0);
                }
            }
    }

    private void RefreshModeStatus(int mModeStatus, int mStatus) {
//        if(CurrentMode != 2)
        if(mModel == 1 && mModeStatus == 0){
            mModeStatus = 1;
        }
        CurrentMode = mModeStatus;
        SetStatus_Text(CurrentMode);
        SetStatus_Counter(CurrentMode);
        SetStatus_Icon(CurrentMode, mStatus);

        if(mTextDesc.getText() == "") mTextDesc.setVisibility(GONE);
        else  mTextDesc.setVisibility(VISIBLE);
//        switch (mModeStatus){
//            case -2:
//                break;
//            case -1:
//                break;
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//        }
    }

    private void SetStatus_Text(int mModeStatus) {
        if(mModel == 0) {
            switch (mModeStatus) {
                case -2:
                    mText.setText(mTextMax);
                    mTextDesc.setText(mTextMax);
                    break;
                case -1:
                    mText.setText(mTextInventory);
                    mTextDesc.setText("");
                    mTextDesc.setVisibility(GONE);
                    break;
                case 0:
                    if (mValue == 0)
                        mText.setText(mTextNormal);
                    else
                        mText.setText(mTextEditCart);

                    mTextDesc.setText(mTextDescription);
                    break;
                case 1:
                    mText.setText("");
                    mTextDesc.setText("");
                    break;
            }
        }else {
            mText.setText("");
            mText.setVisibility(GONE);
            mTextDesc.setText("");
            mTextDesc.setVisibility(GONE);
        }
    }

    private void SetStatus_Counter(int mModeStatus) {
        mIconE.setImageDrawable(null);
        mCounterRly.setVisibility(GONE);

        if (mModeStatus == 1 || mModel == 1) {
//            case 1:
            mTextCnt.setVisibility(VISIBLE);
            if (mValue <= mMin) {
                if (mModel != 1) {
                    mTextDesc.setVisibility(VISIBLE);
                    mTextDesc.setText(mTextMin);
                }
                mIconE.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
                if (mValue == 0) {
                    mTextDesc.setText("");
                    mIconE.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24_disable);
                }
            } else {
                mTextDesc.setText("");
                mTextDesc.setVisibility(GONE);
                mIconE.setImageResource(R.drawable.ic_baseline_remove_24);
            }
//        }else if(mModeStatus == 2){
//            mTextDesc.setText("");
//            mTextDesc.setVisibility(GONE);
//            mIconE.setImageResource(R.drawable.ic_baseline_remove_24);
        }else {
//                break;
//            default:
//                YoYo.with(Techniques.DropOut)
//                        .duration(300)
//                        .onEnd(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//                            }
//                        })
//                        .playOn(mTextCnt);
            mTextCnt.setVisibility(GONE);

            if (mValue > 0 || mValueOther > 0) {
                mCounterRly.setVisibility(VISIBLE);
                YoYo.with(Techniques.DropOut)
                        .duration(400)
                        .playOn(mCounterRly);
            } else {
                mIconE.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
                YoYo.with(Techniques.FadeInRight)
                        .duration(700)
                        .playOn(mIconE);
            }
        }
//                break;
    }

    private final Runnable resetToNormalMode = new Runnable() {
        @Override
        public void run() {
            if(mModel != 1)
                RefreshModeStatus(0, 0);
        }
    };

    private void SetStatus_Icon(int modeStatus, int status) {
        mModeStatus = modeStatus;
        circleView_Ed.setFillColor(mActFillColorDefault);
        circleView_St.setFillColor(mActFillColorDefault);
        mTxtCntAll.setVisibility(GONE);
        mTxtCntCur.setVisibility(GONE);
        if(mYoYo != null) mYoYo.stop();
        if(mYoYo2 != null) mYoYo2.stop();

        if(mModeStatus == 1 || status < 0) {
            if (status > 0 && mMax > 0 && mValue + mJump > mMax)
                mModeStatus = -2;
            else if (status > 0 && ((!JumpTask && mValue + mJump > mInventory) || (JumpTask && !mInvAdd)))
                mModeStatus = -1;
            else if (status != 0)
            {
                if (status > 0) {
                    mProgress.setIndeterminate(true);
                    mProgressEnd.setIndeterminate(true);
                    mProgressEnd.setIndeterminate(true);
                    mProgressStart.setIndeterminate(true);
                    mProgressStart.setIndeterminate(true);
                }

                Techniques mAnim = Techniques.RotateIn;
                if (status < 0) mAnim = Techniques.RotateInUn;
                int mRepeat = 0;
                if(JumpTask) mRepeat = Animation.INFINITE;
                Techniques finalMAnim = mAnim;
                int finalMRepeat = mRepeat;
                mYoYo = YoYo.with(mAnim)
                        .duration(700)
                        .repeat(mRepeat)
                        .onEnd(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {
                                mProgress.setIndeterminate(false);
                                mProgressEnd.setIndeterminate(false);
                                mProgressStart.setIndeterminate(false);
                            }
                        })
                        .onStart(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {
                                if (mValue == 0) {
                                    if (status > 0)
                                        YoYo.with(Techniques.SlideInLeft)
                                                .duration(900)
                                                .repeat(0)
                                                .playOn(mIconE);
                                    else
                                        YoYo.with(Techniques.RubberBand)
                                                .duration(900)
                                                .repeat(0)
                                                .playOn(mIconE);
                                } else if (mValue == mMin) {
                                    if (status > 0)
                                        YoYo.with(Techniques.Wave)
                                                .duration(900)
                                                .repeat(0)
                                                .playOn(mIconE);
                                    else
                                        YoYo.with(Techniques.Flash)
                                                .duration(900)
                                                .repeat(0)
                                                .playOn(mIconE);
                                } else {
                                    mYoYo2 = YoYo.with(finalMAnim)
                                            .duration(700)
                                            .repeat(finalMRepeat)
                                            .playOn(mIconE);
                                }
                            }
                        })
                        .playOn(mIcon);
            }
        }
        else {
            YoYo.with(Techniques.FadeIn)
                    .duration(200)
                    .repeat(0)
                    .playOn(mIcon);
        }

        if(mAnimate != null) mAnimate.stop();

        switch (mModeStatus){
            case -2:
                mIcon.setImageResource(R.drawable.ic_baseline_block_24);
                if (mModel == 1)
                    mModeStatus = 1;
                else{
                    mTextDesc.setText(mTextMax);
                }
                postDelayed(resetToNormalMode, 3000);
                break;
            case -1:
                mIcon.setImageResource(R.drawable.ic_baseline_cancel_24);
                if (mModel == 1)
                    mModeStatus = 1;
                else{
                    mTextDesc.setText(mTextInventory);
                    if (mText.getText().equals(mTextInventory)) mTextDesc.setText("");
                }
                postDelayed(resetToNormalMode, 3000);
                break;
            case 0:
                if(mValue == 0) {
                    mIcon.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                    mAnimate = YoYo.with(Techniques.Flash)
                            .duration(2000)
                            .repeat(Animation.INFINITE)
                            .playOn(mIcon);
                }else {
//                    mTxtCntAll.setVisibility(VISIBLE);
                    mTxtCntCur.setVisibility(VISIBLE);
                    mIcon.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
//                    mAnimate = YoYo.with(Techniques.Swing)
//                            .delay(4000)
//                            .duration(8000)
//                            .repeat(Animation.INFINITE)
//                            .playOn(findViewById(R.id.rlyIconSha));
//                    mAnimate = YoYo.with(Techniques.Swing)
//                            .delay(4000)
//                            .duration(8000)
//                            .repeat(Animation.INFINITE)
//                            .playOn(findViewById(R.id.rlyTextSha));
                }
                break;
            case 1:
                circleView_Ed.setFillColor(mActFillColor);
                circleView_St.setFillColor(mActFillColor);
                mIcon.setImageResource(R.drawable.ic_baseline_add_24);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMinimumHeight(this.getHeight());
        this.setMinimumWidth(this.getWidth());
    }

    public SpCartButton setConfig(double valueCart, double valueOther, double value, double inventory, double jump, double min, double max, boolean invAdd){
        this.mValueCart = valueCart;
        this.mValueOther = valueOther;
        this.mValue = value;
        this.mInvAdd = invAdd;
        this.mInventory = inventory;
        this.mJump = jump;
        this.mMin = min;
        this.mMax = max;
//        CheckJump()
//        RefreshModeStatus(-1, 0);
        RefreshValueText(true);
        postDelayed(resetToNormalMode, 100);
        return this;
    }

    public SpCartButton resetValues(double valueCart, double value, double inventory, boolean invAdd){
        this.inTask = false;
        this.mValueCart = valueCart;
        this.mValue = value;
        this.mInvAdd = invAdd;
        this.mInventory = inventory;
//        CheckJump(0);
        RefreshValueText(false);
//        RefreshModeStatus(0, 0);
        postDelayed(resetToNormalMode, 3000);
        return this;
    }

    public SpCartButton setFont(int font){
        fontFamilyId = font;
        if (fontFamilyId > 0) {
            mText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
            mTextDesc.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        }
        return this;
    }

    public SpCartButton setFaceText(Typeface typeFace, float textSize, int color){
        mText.setTypeface(typeFace);
        if(textSize >= 0) mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if(color >= 0) mText.setTextColor(color);
        return this;
    }

    public SpCartButton setFaceTextDesc(Typeface typeFace, float textSize, int color){
        mTextDesc.setTypeface(typeFace);
        if(textSize >= 0) mTextDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if(color >= 0) mTextDesc.setTextColor(color);
        return this;
    }

    public double getValue(){
        return this.mValue;
    }

    public SpCartButton setCurrentMode(int currentMode){
        this.CurrentMode = currentMode;
        return this;
    }

    public int getCurrentMode(){
        return CurrentMode;
    }

    public SpCartButton setModeStatus(int currentMode){
        mModeStatus = currentMode;
        RefreshModeStatus(mModeStatus, 0);
        return this;
    }

    public SpCartButton setJumpTask(boolean jumpTask){
        JumpTask = jumpTask;
        return this;
    }

    public SpCartButton setText(String textAddToCart, String textEditCart, String textMin, String textMax, String textNoInventory){
        mTextNormal = textAddToCart;
        mTextEditCart = textEditCart;
        mTextMin = textMin;
        mTextMax = textMax;
        mTextInventory = textNoInventory;
        mText.setText(textAddToCart);
        return this;
    }

    public SpCartButton setTextDescription(String text){
        mTextDescription = text;
        mTextDesc.setText(text);
        return this;
    }

    public SpCartButton setActFillColor(int color){
        mActFillColor = color;
        return this;
    }

//    public SpCartButton setInfoKeyShow(boolean infoKeyShow, Drawable changeIcon){
//        if(infoKeyShow) {
//            mProgress.setVisibility(GONE);
//            vIconInfo.setVisibility(VISIBLE);
//        }
//        else vIconInfo.setVisibility(GONE);
//        if(changeIcon != null) vIconInfo.setImageDrawable(changeIcon);
//        return this;
//    }
//
//    public SpCartButton setInfoKeyShowOnStable(boolean infoKeyShowOnStable, Drawable changeIcon){
//        mInfoKeyShowOnStable = infoKeyShowOnStable;
//        if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
//        if(changeIcon != null) vIconInfo.setImageDrawable(changeIcon);
//        return this;
//    }

    public interface OnInfoClickListener {
        void onEvent();
    }

    public void setOnInfoClickListener(OnInfoClickListener eventListener) {
        mInfoClickListener = eventListener;
    }

    public interface OnValueChangeListener {
        void onEvent();
    }

    public void setOnValueChangeListener(OnValueChangeListener eventListener) {
        mValueChangeListener = eventListener;
    }

    public interface OnVsClickListener {
        void onEvent();
    }

    public void setOnVsClickListener(OnVsClickListener eventListener) {
        mVsClickListener = eventListener;
    }

    public interface OnVeClickListener {
        void onEvent();
    }

    public void setOnVeClickListener(OnVeClickListener eventListener) {
        mVeClickListener = eventListener;
    }

    public interface OnStartTaskListener {
        void onEvent(double mJump);
    }

    public void setOnStartTaskListener(OnStartTaskListener eventListener) {
        mStartTaskListener = eventListener;
    }


//    public SpCartButton autoBackOnFail(boolean back, long delayMilis, boolean setFailColor){
//        mBackOnFail = back;
//        mMiliDelay = delayMilis;
//        mSetFailColor = setFailColor;
//        return this;
//    }
//
//    public SpCartButton autoBackOnInfo(boolean back, long delayMilisInfo, boolean setInfoColor){
//        mBackOnInfo = back;
//        mMiliDelayInfo = delayMilisInfo;
//        mSetInfoColor = setInfoColor;
//        return this;
//    }
//
//    public SpCartButton setNormal(String text, String textDesc, Drawable drawable, int color){
//        if(text != null) mTextNormal = text;
//        if(textDesc != null) mDescNormal = textDesc;
//        if(drawable != null) mIconNormal = drawable;
//        if(color > 0) mColorNormal = color;
//
//        mText.setText(mTextNormal);
//        mIcon.setImageDrawable(mIconNormal);
//
//        return this;
//    }
//
//    public SpCartButton setProgress(String text, String textDesc, Drawable drawable, int color){
//        if(text != null) mTextProgress = text;
//        if(textDesc != null) mDescProgress = textDesc;
//        if(drawable != null) mIconProgress = drawable;
//        if(color > 0) mColorProgress = color;
//        return this;
//    }
//
//    public SpCartButton setSuccess(String text, String textDesc, Drawable drawable, int color){
//        if(text != null) mTextSuccess = text;
//        if(textDesc != null) mDescSuccess = textDesc;
//        if(drawable != null) mIconSuccess = drawable;
//        if(color > 0) mColorSuccess = color;
//        return this;
//    }
//
//    public SpCartButton setFail(String text, String textDesc, Drawable drawable, int color){
//        if(text != null) mTextFail = text;
//        if(textDesc != null) mDescFail = textDesc;
//        if(drawable != null) mIconFail = drawable;
//        if(color > 0) mColorFail = color;
//        return this;
//    }
//
//    public SpCartButton setInfo(String text, String textDesc, Drawable drawable, int color){
//        if(text != null) mTextInfo = text;
//        if(textDesc != null) mDescInfo = textDesc;
//        if(drawable != null) mIconInfo = drawable;
//        if(color > 0) mColorInfo = color;
//        return this;
//    }
//
//    public SpCartButton setTextDesc(String text, boolean OnSet){
//        if(OnSet) mText.setText(text);
//        mDescFail = text;
//        return this;
//    }
//
//    public SpCartButton setTextPending(String text){
//        mTextProgress = text;
//        return this;
//    }
//
//    public SpCartButton setAnimateDuration(int duration){
//        animSel.setDuration(duration);
//        return this;
//    }
//
//    public SpCartButton setIconPadding(int padding){
//        mIcon.setPadding(padding, padding, padding, padding);
//        return this;
//    }


//    public int getCurrentProgress(){
//        return mProgress.getProgress();
//    }

//    public SpCartButton setProgress(int pMode){
//        CurrentMode = pMode;
//        int syncColor = Color.WHITE;
//        switch (pMode){
//            case 0: //normal
//                mText.setTextColor(getResources().getColor(R.color.colorText));
////                vIconInfo.setImageResource(R.drawable.ic_action_info);
////                vIconInfo.clearAnimation();
//
//                syncColor = mColorNormal;
//                mIcon.clearAnimation();
//                mText.setText(mTextNormal);
//                if(mIconNormal != null)
//                mIcon.setImageDrawable(mIconNormal);
////                mIT.setVisibility(VISIBLE);
////                mProgress.setVisibility(VISIBLE);
////                if(mInfoKeyShowOnStable)
////                    vIconInfo.setVisibility(VISIBLE);
//                break;
//            default: //pending
//                mText.setTextColor(getResources().getColor(R.color.colorText));
////                vIconInfo.setImageResource(R.drawable.ic_action_info);
////                vIconInfo.clearAnimation();
//
//                syncColor = mColorProgress;
//                mIcon.startAnimation(animSel);
//                mIcon.setImageDrawable(mIconProgress);
//                mText.setText(mTextProgress);
////                mProgress.setProgress(pMode);
////                if(mModeStyle > 0) mIT.setVisibility(GONE);
////                vIconInfo.setVisibility(GONE);
////                mProgress.setVisibility(VISIBLE);
//                break;
//            case 100: //success
//                mText.setTextColor(getResources().getColor(R.color.colorText));
////                vIconInfo.setImageResource(R.drawable.ic_action_info);
////                vIconInfo.clearAnimation();
//
//                syncColor = mColorSuccess;
//                mIcon.clearAnimation();
//                mIcon.setImageDrawable(mIconSuccess);
//                mText.setText(mTextSuccess);
////                if(mModeStyle > 0) mIT.setVisibility(GONE);
////                mProgress.setVisibility(GONE);
////                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
//                break;
//            case -1: //fail
//                syncColor = mColorFail;
//                mIcon.clearAnimation();
//                mIcon.setImageDrawable(mIconFail);
//                mText.setText(mTextFail);
////                mIT.setVisibility(VISIBLE);
////                mProgress.setVisibility(GONE);
////                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
//
//                if(mBackOnFail) {
////                    vIconInfo.setVisibility(VISIBLE);
////                    mProgress.setVisibility(GONE);
//
//                    animation_rotate_cb.setDuration(mMiliDelay);
////                    vIconInfo.startAnimation(animation_rotate_cb);
//                    animation_rotate_cb.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            mText.setText(mDescFail);
////                            vIconInfo.setImageResource(R.drawable.ic_action_error);
//                            if(mSetFailColor) mText.setTextColor(getResources().getColor(R.color.colorFail));
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            setProgress(0);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//
//                }
//                break;
//            case 101: //info
//                syncColor = mColorProgress;
//                mIcon.clearAnimation();
//                mText.setText(mTextInfo);
////                mIT.setVisibility(VISIBLE);
////                mProgress.setVisibility(GONE);
////                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
//
//                if(mBackOnInfo) {
////                    vIconInfo.setVisibility(VISIBLE);
////                    mProgress.setVisibility(GONE);
//
//                    animation_blink.setDuration(mMiliDelayInfo);
////                    vIconInfo.startAnimation(animation_blink);
//                    animation_blink.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            mText.setText(mDescInfo);
////                            vIconInfo.setImageResource(R.drawable.ic_action_info);
//                            if(mSetInfoColor) mText.setTextColor(getResources().getColor(R.color.colorInfo));
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            CurrentMode = 0;
//                            mText.setText(mTextNormal);
//                            mText.setTextColor(getResources().getColor(R.color.colorText));
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//
//                }
//                break;
//        }
//
//        if(mColorSet) {
//            GradientDrawable backgroundGradient = (GradientDrawable) mViewBase.getBackground();
//            backgroundGradient.setColor(syncColor);
//        }
//
//        return this;
//    }

}