package com.casetable.casetable.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.casetable.casetable.Api.WebService;
import com.casetable.casetable.Db.MIDatabaseHandler;
import com.casetable.casetable.Model.CaseAddModel;
import com.casetable.casetable.Model.CaseEditModel;
import com.casetable.casetable.R;
import com.casetable.casetable.utils.CommanDialog;
import com.casetable.casetable.utils.CommonMethods;
import com.casetable.casetable.utils.MySpannable;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class CaseDetailsFragment extends Fragment implements View.OnClickListener {

    EditText edit_case, edit_lawfirm,edit_caseno, edit_caseamount, edit_casestage, edit_excasestage;
    Button button_save;
    MIDatabaseHandler databaseHandler;
    static Bundle bundle;
    String CaseID;
    TextView expandable_casestage,expandable_excasestage;

    public CaseDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailscase, container, false);

        Init(v);

        return v;
    }

    public void Init(View v) {

        bundle = this.getArguments();



        databaseHandler=new MIDatabaseHandler(getActivity());

        expandable_casestage= (TextView)v.findViewById(R.id.expandable_casestage);
        expandable_excasestage= (TextView)v.findViewById(R.id.expandable_excasestage);
        edit_case = (EditText) v.findViewById(R.id.edit_case);
        edit_case.setKeyListener(null);
        edit_caseno = (EditText) v.findViewById(R.id.edit_caseno);
        edit_caseno.setKeyListener(null);
        edit_lawfirm = (EditText) v.findViewById(R.id.edit_lawfirm);
        edit_lawfirm.setKeyListener(null);
        edit_caseamount = (EditText) v.findViewById(R.id.edit_caseamount);
        edit_caseamount.setKeyListener(null);
        edit_casestage = (EditText) v.findViewById(R.id.edit_casestage);
        edit_casestage.setKeyListener(null);
        edit_excasestage = (EditText) v.findViewById(R.id.edit_excasestage);
        button_save = (Button) v.findViewById(R.id.button_save);
        button_save.setOnClickListener(this);


        if(bundle!=null)
        {



                CaseID=bundle.getString("CaseID");
                edit_case.setText(bundle.getString("CaseName"));
                edit_caseno.setText(bundle.getString("CaseNo"));
                edit_lawfirm.setText(bundle.getString("LawFirm"));
                edit_caseamount.setText(bundle.getString("CaseAmount"));

            expandable_casestage.setText(bundle.getString("CaseStage"));
            makeTextViewResizable(expandable_casestage, 3, "View More", true);

            expandable_excasestage.setText(bundle.getString("CaseNextstage"));
            makeTextViewResizablenext(expandable_excasestage, 3, "View More", true);



        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MainActivity.tv_title.setText("Case Details");
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.button_save:

                if(!MainActivity.NetworkChangeReceiver.isOnline(getActivity()))
                {
                    CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(),"Please check your internet connection or try again later!");

                }else{

                    if (edit_case.getText().toString().trim().length() == 0) {
                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Case!");

                    } else if (edit_caseno.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Case No.!");

                    } else if (edit_caseamount.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Claim Amount!");

                    }
                    else if (edit_casestage.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Current Stage!");

                    }
                    else if (edit_excasestage.getText().toString().trim().length() == 0) {

                        CommanDialog.displaydialogwithouttitlehoutnegative(getActivity(), "Please Enter Expected Next Stage!");

                    }
                    else {




                    }

                }
                break;
        }
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(true){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());

                            tv.setText(bundle.getString("CaseStage"), TextView.BufferType.SPANNABLE);


                        tv.invalidate();

                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());

                            tv.setText(bundle.getString("CaseStage"), TextView.BufferType.SPANNABLE);


                        tv.invalidate();

                        makeTextViewResizable(tv, 3, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }





    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }





    private  SpannableStringBuilder addClickablePartTextViewResizablenext(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(true){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());

                        tv.setText(bundle.getString("CaseNextstage"), TextView.BufferType.SPANNABLE);


                        tv.invalidate();

                        makeTextViewResizablenext(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());

                        tv.setText(bundle.getString("CaseNextstage"), TextView.BufferType.SPANNABLE);


                        tv.invalidate();

                        makeTextViewResizablenext(tv, 3, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }





    public void makeTextViewResizablenext(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

        ViewTreeObserver vtonext = tv.getViewTreeObserver();
        vtonext.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizablenext(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizablenext(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizablenext(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

}
