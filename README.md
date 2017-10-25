# SuperCalendar


<div style = "float:center">
    <img src="http://osnftsiae.bkt.clouddn.com/syllabus_1.png" width="240">
    <img src="http://osnftsiae.bkt.clouddn.com/syllabus_2.png" width="240">
<div/>
<div style = "float:center">
    <img src="http://osnftsiae.bkt.clouddn.com/supercalendarexample.gif" width="240">
<div/>

RecyclerView的layout_behavior为com.ldf.calendar.behavior.RecyclerViewBehavior

```xml
 <android.support.design.widget.CoordinatorLayout
        android:id="@+id/content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1">

        <com.ldf.calendar.view.MonthPager
            android:id="@+id/calendar_view"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            android:background="#fff">
        </com.ldf.calendar.view.MonthPager>

        <android.support.v7.widget.RecyclerView
            android:id="@+id/list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_behavior="com.ldf.calendar.behavior.RecyclerViewBehavior"
            android:background="#c2c2c2"
            android:layout_gravity="bottom"/>

    </android.support.design.widget.CoordinatorLayout>
    

	@Override
    public void refreshContent() {
        super.refreshContent();
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context , layoutResource);
    }


	CustomDayView customDayView = new CustomDayView(
        	context , R.layout.custom_day);
	calendarAdapter = new CalendarViewAdapter(
                context ,
                onSelectDateListener ,
                Calendar.MONTH_TYPE ,
                customDayView);

@Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        initCalendarView();
    }
    
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(
        	context , R.layout.custom_day);
		calendarAdapter = new CalendarViewAdapter(
                context ,
                onSelectDateListener ,
                Calendar.MONTH_TYPE ,
                customDayView);
        initMarkData();
        initMonthPager();
    } 

private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                //your code
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                monthPager.selectOtherMonth(offset);
            }
        };
    }

private void initMarkData() {
       HashMap markData = new HashMap<>();
       markData.put("2017-8-9" , "1");
       markData.put("2017-7-9" , "0");
       markData.put("2017-6-9" , "1");
       markData.put("2017-6-10" , "0");
       calendarAdapter.setMarkData(markData);
   }

monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getAllItems();
                if(currentCalendars.get(position % currentCalendars.size()) instanceof Calendar){
                    //you code
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !initiated) {
            CalendarDate today = new CalendarDate();
        	calendarAdapter.notifyDataChanged(today);
            initiated = true;
        }
    }


