# Transformations

1. Two easy code for understand how to use map or switchMap of Transfromation for liveData

### public functions of Transfromation 

 - map : Applies the given function on the main thread to each value emitted by source LiveData and returns LiveData, which emits resulting values.
 
 - switchMap : can be use to query part of liveData in ListLiveData
 
### Demo requirements about map:

 - A customer model such as User, have members : firstName, lastName, old, sex
 
 - A MutableLiveData<User> be mapped to a LiveData fullName equals firstName + " " + lastName
 
 - Show fullName on a textView
 
### code about map:

Define customer dataModel, User

	public class User {
        private String firstName;
        private String lastName;
        private int old;
        private String sex;
	    
        public User(String firstName, String lastName, int old, String sex){
            this.firstName = firstName;
            this.lastName = lastName;
            this.old = old;
            this.sex = sex;
        }
		...
		get, set methods
    }

ViewModel 	

	// Init MutableLiveData and map to fullName
    MutableLiveData<User> liveDataUser = new MutableLiveData<>();
    LiveData<String> fullName = Transformations.map(liveDataUser, TestViewModel::processData);

    // Define function, processData
	private static String processData(User user) {
        if (user.getFirstName().isEmpty()|| user.getLastName().isEmpty()) {
            return "Your Full name is invalid";
        } else {
            return "Your Full name is " + user.getFirstName() + " " + user.getLastName();
        }
    }
	
	// Define get, set function
	public LiveData<String> getFullName(){
        return fullName;
    }

    public void setLiveDataUser(User user){
        liveDataUser.setValue(user);
    }
	
View (Activity)

    // init viewModel
	testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
	
	// observe mapped liveData
	testViewModel.getFullName().observe(this, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            Timber.e("TransMap = ", s);
            textView.setText(s);
        }
    });
	
	// setValue to MutableLiveData
	testViewModel.setLiveDataUser(new User("Jin", "Huang", 25, "man"));

### references - map

 - https://developer.android.com/reference/android/arch/lifecycle/Transformations
 
 - https://github.com/benedictcontawe/JetpackComponentsApp/blob/Transformations_Map/app/src/main/java/com/example/jetpackcomponentsapp/MainViewModel.java


### Demo requirements about switchMap:

 - when text of textView is "1" or others, query "2" that equals value of a User's firstName in UserList and show firstName on TextView
 
 - when text of textView is "2", query "1" that equals value of a User's firstName in UserList and show firstName on TextView
 
### code about switchMap: 

ViewModel and repository:

	// a list to simulate UserList data from repositoryAPI 
    static ArrayList<User> userList;
	// Demo repository Data
    public void initUserList(){
        userList = new ArrayList<>();
        userList.add(new User("1", "1", 1, "1"));
        userList.add(new User("2","2", 2, "2"));
    }
	
	// a liveData to be a query parameters
	MutableLiveData<String> liveDataUserFirstName = new MutableLiveData<>();
	// a query result in UserList
    LiveData<User> liveDataUser2 = Transformations.switchMap(liveDataUserFirstName, TestViewModel::getUserByFirstName);
	
	// define method to query result
	private static LiveData<User> getUserByFirstName(String id){
        Timber.e("id = %s", id);
        for (int i = 0; i < userList.size(); i ++){
            if (userList.get(i).getFirstName().equalsIgnoreCase(id))
                return new MutableLiveData<User>(userList.get(i));
        }
        return new MutableLiveData<User>(
                new User("Empty", "User", 0, "to get"));
    }
	
	// get a Transformation liveData by switchMap to observe
    public LiveData<User> getLiveDataUser2(){
        return liveDataUser2;
    }
	
	// set liveData id to trigger function to update liveDataList
    // and just return part of data as a liveData relationship with id
    public void setLiveDataUserFirstName(String id){
        liveDataUserFirstName.setValue(id);
    }
	
View :

	testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);	
	testViewModel.initUserList();
	
	// observe liveDataUser2
	testViewModel.getLiveDataUser2().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Timber.e("Trans.switchMap a user by id = %s, %s, %d, %s", user.getFirstName(), user.getLastName()
                , user.getOld(), user.getSex());
                textView.setText(user.getFirstName());
            }
        });
	
	// onClick:
	...
	testViewModel.setLiveDataUserFirstName(textView.getText().toString().equals("1")? "2" : "1");

	
### reference - switchMap 
 
 - https://developer.android.com/reference/android/arch/lifecycle/Transformations
 
 - https://codinginfinite.com/android-livedata-transformation-example/
 
