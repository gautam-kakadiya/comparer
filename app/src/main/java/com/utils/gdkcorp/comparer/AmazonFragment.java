package com.utils.gdkcorp.comparer;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmazonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmazonFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RviewAdapter adapter;
    private RecyclerView recyclerview;
    private LinearLayoutManager layoutmanager;
    private List<AmazonProductInfo> datalist = new ArrayList<>();
    private ImageButton firstPage,previousPage,nextPage,lastPage;
    private TextView currentPage,totalPage;
    public int pageNo = 1;

    public AmazonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmazonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmazonFragment newInstance(String param1, String param2) {
        AmazonFragment fragment = new AmazonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_amazon, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.amazon_recyclerview);
        adapter = new RviewAdapter(getActivity(),datalist);
        layoutmanager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutmanager);
        recyclerview.setAdapter(adapter);
        firstPage = (ImageButton) view.findViewById(R.id.page_first);
        nextPage = (ImageButton) view.findViewById(R.id.page_next);
        previousPage = (ImageButton) view.findViewById(R.id.page_previous);
        lastPage = (ImageButton) view.findViewById(R.id.page_last);
        currentPage = (TextView) view.findViewById(R.id.current_page);
        totalPage = (TextView) view.findViewById(R.id.total_page);
        //firstPage.setTag(KEY_FIRST_PAGE);
        firstPage.setOnClickListener(this);
       // previousPage.setTag(KEY_PREVIOUS_PAGE);
        previousPage.setOnClickListener(this);
       // nextPage.setTag(KEY_NEXT_PAGE);
        nextPage.setOnClickListener(this);
        //lastPage.setTag(KEY_LAST_PAGE);
        lastPage.setOnClickListener(this);
        Log.d("adapter-1","onview created");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void startAmazonTask(String keyword){
            Log.d("keyword",keyword);
            URL url = getURL(keyword);
            Log.d("URL",url.toString());
            AmazonFetchTask task = new AmazonFetchTask();
            task.execute(url);


    }

    private URL getURL(String keyword) {
        UrlParameterHandler urlParameterHandlerdler= UrlParameterHandler.getInstance();
        Map<String,String>  params = urlParameterHandlerdler.buildMapForItemSearch(keyword,null);
        SignedRequestsHelper signedRequestHelper= null;
        try {
            signedRequestHelper = SignedRequestsHelper.getInstance();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String urlstring = signedRequestHelper.sign(params);
        Log.d("urlstring",urlstring);
        URL url = null;
        try {
            url = new URL(urlstring);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.page_first :
                                    break;
            case R.id.page_previous :
                                    break;
            case R.id.page_next :
                                    break;
            case R.id.page_last :
                                    break;
            default:
                        break;
        }

    }

    public class AmazonFetchTask extends AsyncTask<URL, AmazonProductInfo, Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(URL... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();
                connection.setRequestMethod("GET");
                InputStream inputstream = connection.getInputStream();
                process(inputstream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void process(InputStream inputstream) {
            DocumentBuilderFactory documentbuilderfactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentbuilderfactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document xmlDocument = null;
            try {
                xmlDocument = documentBuilder.parse(inputstream);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element rootElement = xmlDocument.getDocumentElement();
            Log.d("rootElemet",rootElement.getTagName());
            NodeList itemlist= rootElement.getElementsByTagName("Item");
            NodeList itemChidren=null;
            NodeList AttrChildren=null;
            Node currentItem = null;
            Node currentAttr = null;
            Node currentChild = null;
            for(int i=0;i<itemlist.getLength();++i){
                AmazonProductInfo current = new AmazonProductInfo();
                currentItem = itemlist.item(i);
                itemChidren = currentItem.getChildNodes();
                for(int j=0;j<itemChidren.getLength();++j){
                    currentChild=itemChidren.item(j);
                    if(currentChild.getNodeName().equalsIgnoreCase("DetailPageURL")){
                        Log.d("DetalPageUrl",currentChild.getTextContent());
                        current.setDetailUrl(currentChild.getTextContent());
                    }
                    if(currentChild.getNodeName().equalsIgnoreCase("ImageSets")){
                        NodeList imagesetlist = currentChild.getFirstChild().getChildNodes();
                        for(int l=0;l<imagesetlist.getLength();++l){
                            Node childimgset = imagesetlist.item(l);
                            Log.d("SmallImageUrl",childimgset.getNodeName());
                            if(childimgset.getNodeName().equalsIgnoreCase("MediumImage")){
                                Log.d("SmallImageUrl",childimgset.getFirstChild().getTextContent());
                                current.setImageURL(childimgset.getFirstChild().getTextContent());
                            }
                        }
                    }
                    if(currentChild.getNodeName().equalsIgnoreCase("ItemAttributes")){
                        AttrChildren = currentChild.getChildNodes();
                        for(int k=0;k<AttrChildren.getLength();++k){
                            currentAttr=AttrChildren.item(k);
                            if(currentAttr.getNodeName().equalsIgnoreCase("ListPrice")){
                                current.setPrice(currentAttr.getChildNodes().item(2).getTextContent());
                            }
                            else if(currentAttr.getNodeName().equalsIgnoreCase("Brand")){
                                current.setBrand(currentAttr.getTextContent());
                            }
                            else if(currentAttr.getNodeName().equalsIgnoreCase("Title")){
                                current.setTitle(currentAttr.getTextContent());
                            }
                        }
                    }
                }
                publishProgress(current);
            }

        }

        @Override
        protected void onProgressUpdate(AmazonProductInfo... values) {
            adapter.addItem(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

}
