package com.chenghui.ekaxin.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.NotepadListAdaper;
import com.chenghui.ekaxin.bean.NotepadMessage;
import com.chenghui.ekaxin.ui.EditMemoActivity;
import com.chenghui.ekaxin.ui.FragmentBase;


public class NotepadFragment extends FragmentBase{
	Button btnAddItem;
	ListView lv_nopetad;
	private NotepadListAdaper notepadListAdaper;
	List<List<NotepadMessage>> notepadList = new ArrayList<List<NotepadMessage>>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_notepad, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initNopetadList();
		initNopetadOtherUi();
	}
	
	public void initNopetadOtherUi(){
		btnAddItem = (Button)findViewById(R.id.btn_add_item); 
		
		btnAddItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimActivity(EditMemoActivity.class);
			}
		});
	}
	
	public void initNopetadList(){
		lv_nopetad = (ListView) findViewById(R.id.lv_notepad);
		notepadListAdaper = new NotepadListAdaper(getActivity(), notepadList);
		lv_nopetad.setAdapter(notepadListAdaper);
	}
	
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	/**
	 *  重启更新
	 */
	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryMyNotepad();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @ClassName: queryMyNotepad
	 * @Description: 获取所有的便签信息
	 * @author kcj
	 * @date 
	 */

	private void queryMyNotepad() {
		List<NotepadMessage> message = new ArrayList<NotepadMessage>();
		
		NotepadMessage notepad_1 = new NotepadMessage();
		notepad_1.setType("旅游");
		notepad_1.setTitle("深圳一日游1");
		notepad_1.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_1.setDate("2014-11-11");
		message.add(notepad_1);
		
		NotepadMessage notepad_2 = new NotepadMessage();
		notepad_2.setType("旅游");
		notepad_2.setTitle("深圳一日游2");
		notepad_2.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_2.setDate("2014-11-11");
		message.add(notepad_2);
		
		NotepadMessage notepad_3 = new NotepadMessage();
		notepad_3.setType("旅游");
		notepad_3.setTitle("深圳一日游3");
		notepad_3.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_3.setDate("2014-11-11");
		message.add(notepad_3);
		
		NotepadMessage notepad_4 = new NotepadMessage();
		notepad_4.setType("旅游");
		notepad_4.setTitle("深圳一日游4");
		notepad_4.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_4.setDate("2014-11-11");
		message.add(notepad_4);
		
		NotepadMessage notepad_5 = new NotepadMessage();
		notepad_5.setType("吃的");
		notepad_5.setTitle("深圳一日游5");
		notepad_5.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_5.setDate("2014-11-11");
		message.add(notepad_5);
		
		NotepadMessage notepad_6 = new NotepadMessage();
		notepad_6.setType("喝的");
		notepad_6.setTitle("深圳一日游6");
		notepad_6.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_6.setDate("2014-11-11");
		message.add(notepad_6);
		
		NotepadMessage notepad_7 = new NotepadMessage();
		notepad_7.setType("喝的");
		notepad_7.setTitle("深圳一日游7");
		notepad_7.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_7.setDate("2014-11-11");
		message.add(notepad_7);
		
		NotepadMessage notepad_8 = new NotepadMessage();
		notepad_8.setType("旅游");
		notepad_8.setTitle("深圳一日游8");
		notepad_8.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_8.setDate("2014-11-11");
		message.add(notepad_8);
		
		NotepadMessage notepad_9 = new NotepadMessage();
		notepad_9.setType("喝的");
		notepad_9.setTitle("深圳一日游9");
		notepad_9.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_9.setDate("2014-11-11");
		message.add(notepad_9);
		
		NotepadMessage notepad_10 = new NotepadMessage();
		notepad_10.setType("喝的");
		notepad_10.setTitle("深圳一日游10");
		notepad_10.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_10.setDate("2014-11-11");
		message.add(notepad_10);
		
		NotepadMessage notepad_11 = new NotepadMessage();
		notepad_11.setType("吃的");
		notepad_11.setTitle("深圳一日游11");
		notepad_11.setContent("kjdklajsdasdjaskdjasldjklasdj");
		notepad_11.setDate("2014-11-11");
		message.add(notepad_11);
		
		filledData(message);
		if (notepadListAdaper == null) {
			notepadListAdaper = new NotepadListAdaper(getActivity(), notepadList);
			lv_nopetad.setAdapter(notepadListAdaper);
		} else {
			notepadListAdaper.notifyDataSetChanged();
		}

	}
	
	int itype = 3;
	/**
	 * 为ListView填充数据
	 * @param date
	 * @returnList <NotepadMessage> datas
	 */
	private void filledData(List<NotepadMessage> datas) {
		List<NotepadMessage> notepadMessage;
		int total = datas.size();
		for(int i = 0; i < itype ; i++){
			notepadMessage = new ArrayList<NotepadMessage>();
			for(int j = 0 ; j < total ; j++){
				if(i==0){
					NotepadMessage notepad = datas.get(j);
					NotepadMessage message1 = new NotepadMessage();
					if(notepad.getType().contains("旅游")){
						message1.setType(notepad.getType());
						message1.setTitle(notepad.getTitle());
						message1.setContent(notepad.getContent());
						message1.setDate(notepad.getDate());
						notepadMessage.add(message1);
					}
				}
				if(i==1){
					NotepadMessage notepad = datas.get(j);
					NotepadMessage message1 = new NotepadMessage();
					if(notepad.getType().contains("吃的")){
						message1.setType(notepad.getType());
						message1.setTitle(notepad.getTitle());
						message1.setContent(notepad.getContent());
						message1.setDate(notepad.getDate());
						notepadMessage.add(message1);
					}
				}
				if(i==2){
					NotepadMessage notepad = datas.get(j);
					NotepadMessage message1 = new NotepadMessage();
					if(notepad.getType().contains("喝的")){
						message1.setType(notepad.getType());
						message1.setTitle(notepad.getTitle());
						message1.setContent(notepad.getContent());
						message1.setDate(notepad.getDate());
						notepadMessage.add(message1);
					}
				}
			}
			filledDataList(notepadMessage);	
		} 
	}
	
	public void filledDataList(List<NotepadMessage> messageList){
		List<NotepadMessage> notepadMessage1 = new ArrayList<NotepadMessage>();
		List<NotepadMessage> notepadMessage2 = new ArrayList<NotepadMessage>();
		if(messageList.size() == 0){
			return;
		}
		int num = (messageList.size()-1)/3+1;
		if(num == 1 ){

			for(int i = 0 ;i < messageList.size() ; i++){
				NotepadMessage data = messageList.get(i);
				notepadMessage1.add(data);
			}
			notepadList.add(notepadMessage1);
		}
		
		if(num == 2){
			for(int i = 0 ;i < 3 ; i++){
				NotepadMessage data = messageList.get(i);
				notepadMessage1.add(data);
			}
			notepadList.add(notepadMessage1);
			
			for(int j = 3 ;j < messageList.size() ; j++){
				NotepadMessage data = messageList.get(j);
				notepadMessage2.add(data);
			}
			notepadList.add(notepadMessage2);
		}
	}
}
