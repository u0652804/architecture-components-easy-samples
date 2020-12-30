# MediatorLiveData

A easy sample for understand how to use MediatorLiveData

### public functions of MediatorLiveData 

 - addSource : Starts to listen the given source LiveData, onChanged observer will be called when source value was changed.
 
 - removeSource : Stops to listen the given LiveData.

### Demo requirements :

 - Show a count value on UI.

 - When click TextView, count + 1.

 - When count < 15, use liveData1 to update data and observer to update UI change.

 - When count >= 15 and count < 25, color of text will be red color, use liveData2 to update data and observer to update UI change.
 
 
### presudo code :

init liveData and MediatorLiveData

    MediatorLiveData liveDataMerger;
    MutableLiveData<Integer> liveData1;
    MutableLiveData<Integer> liveData2;
    
    liveData1 = new MediatorLiveData<>();
    liveData2 = new MediatorLiveData<>();
    liveDataMerger = new MediatorLiveData<>();    

add liveData to MediatorLiveData

    liveDataMerger.addSource(liveData1, new Observer<Integer>() {

            @Override
            public void onChanged(Integer integer) {
                //... do something
                // liveDataMerger.setValue(...) to trigger onChange
            }
        });   
	
remove liveData added to MediatorLiveData

	liveDataMerger.removeSource(liveData1);

init viewModel and observe MediatorLiveData

     private TestViewModel testViewModel; 
     testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        testViewModel.getLiveDataMerger().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //... do something about UI update
            }
        });   

### references

 - https://developer.android.com/reference/android/arch/lifecycle/MediatorLiveData
 
 - https://ithelp.ithome.com.tw/articles/10222799
 
 - https://medium.com/better-programming/everything-to-should-understand-about-livedata-507dd83adea7
 
 - https://stackoverflow.com/questions/46930335/what-is-difference-between-mediatorlivedata-and-mutablelivedata-in-mvvm
