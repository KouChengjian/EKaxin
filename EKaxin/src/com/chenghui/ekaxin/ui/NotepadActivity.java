package com.chenghui.ekaxin.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.NotesSqlistAdapter;
import com.chenghui.ekaxin.util.NotepadInfo;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;

/**
 * @ClassName: NotepadActivity
 * @Description: ���±�
 * @author kcj
 * @date 2014-9-12 10:20
 */
public class NotepadActivity extends BaseActivity {
	private String[] deviceFrom;
	private int[] deviceTo;
	// ��ȡ����,����������10������,ʵ�ʿ����п�����Ҫ�����ݿ�������ȡ
	List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();
	// Ϊ��ָ���ļ�ӳ�䱻ָ����ֵ��Ҳ����˵����һ��ӳ�䣬����һ��ָ���ļ�����һ��ָ����ֵ
	Map<String, Object> deviceMap = null;

	public SimpleAdapter notesSetAdapter;

	private ListView ListViewNotes;

	// ����ӵ�ȫ����
	private NotesSqlistAdapter sqlbdAdepter;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_notepad1);
		
		initView();
	}

	public void initView() {
		sqlbdAdepter = new NotesSqlistAdapter(this);
		sqlbdAdepter.open();

		initTopBarForBoth("���±�", R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
						// startAnimActivity(RedactActivity.class);
						Intent intent = new Intent();
						intent.putExtra("tag", "null");
						intent.putExtra("ID", "-1");
						intent.setClass(NotepadActivity.this,
								EditNotepadActivity.class);
						startActivity(intent);
					}
				});
		ListViewNotes = (ListView) findViewById(R.id.notes_list);
		deviceFrom = new String[] { "notes_title", "notes_time", "notes_clock",
				"notes_showpic", "notes_id" };
		deviceTo = new int[] { R.id.notes_titie, R.id.notes_time,
				R.id.notes_clock, R.id.notes_showpicture, R.id.notes_id };

		NotepadInfo[] camera = sqlbdAdepter.queryAllData();
		if (camera == null) {
			Toast.makeText(NotepadActivity.this, "��ǰ����Ϣ���봴���ռ�", Toast.LENGTH_LONG)
					.show();
			return;
		}
		for (int i = 0; i < camera.length; i++) {
			String msg = camera[i].toString();
			String[] temp = null;
			temp = msg.split("-");
			// Toast.makeText(NotesActivity.this,msg,Toast.LENGTH_LONG).show();

			deviceMap = new HashMap<String, Object>();
			deviceMap.put("notes_title", temp[1]);
			deviceMap.put("notes_time", temp[2]);
			if (temp[3].contains("0")) {
				deviceMap.put("notes_clock", R.drawable.status_phone);
			} else {
				deviceMap.put("notes_clock", R.drawable.notes_clock);
			}

			if (temp[5] != null) {
				deviceMap.put("notes_showpic", temp[5]);// "/storage/sdcard0/DCIM/100ANDRO/DSC_0019.jpg"
				// deviceMap.put("notes_showpic",
				// "/storage/sdcard0/DCIM/100ANDRO/DSC_0019.jpg");
			}
			// deviceMap.put("notes_showpic",
			// "/storage/sdcard0/DCIM/100ANDRO/DSC_0019.jpg");
			// ���ݿ�ID
			deviceMap.put("notes_id", temp[0]);
			deviceList.add(deviceMap);
		}

		setAdapter();
	}

	public void setAdapter() {
		notesSetAdapter = new SimpleAdapter(this, deviceList,
				R.layout.notepad_listview_item, deviceFrom, deviceTo) {
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				@SuppressWarnings({ "unchecked", "unused" })
				final HashMap<String, Object> kmap = (HashMap<String, Object>) this
						.getItem(position);
				return view;
			}
		};

		ListViewNotes.setAdapter(notesSetAdapter);
		initListener();
	}

	public void initListener() {
		ListViewNotes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) ListViewNotes
						.getItemAtPosition(arg2);
				String str = map.get("notes_id");
				//Toast.makeText(NotepadActivity.this, "�����" + arg2 + "����Ŀ",Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("tag", "examine");
				intent.putExtra("ID", str);
				intent.setClass(NotepadActivity.this, EditNotepadActivity.class);
				startActivity(intent);
			}
		});

		ListViewNotes
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("ѡ��");
						menu.add(0, 0, 0, "��Ϣ");
						menu.add(0, 1, 0, "�޸�");
						menu.add(0, 2, 0, "ɾ��");
						menu.add(0, 3, 0, "�˳�");
					}
				});
	}

	/**
	 * �����˵� ���item�˵���Ӧ����
	 */
	public boolean onContextItemSelected(MenuItem item) {
		// ��ȡ��ǰ�����µ�item�е�����-id
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		String title = ((TextView) menuInfo.targetView
				.findViewById(R.id.notes_id)).getText().toString();
		int id = Integer.valueOf(title).intValue();

		// ��ȡ��ǰ������λ��
		ContextMenuInfo info = item.getMenuInfo();
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
		int i_position = contextMenuInfo.position; // ��ȡѡ����λ��

		if (item.getItemId() == 0) {

		} else if (item.getItemId() == 1) {

		} else if (item.getItemId() == 2) {
			long result = sqlbdAdepter.deleteOneData(id);
			deviceList.remove(i_position);
			notesSetAdapter.notifyDataSetChanged();
			String msg = "ɾ��IDΪ" + title + "������" + (result > 0 ? "�ɹ�" : "ʧ��");
			Toast.makeText(NotepadActivity.this, msg, Toast.LENGTH_LONG).show();
		} else if (item.getItemId() == 3) {

		}

		return super.onContextItemSelected(item);
	}

}
