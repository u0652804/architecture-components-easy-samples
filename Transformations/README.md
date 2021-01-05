# MediatorLiveData

A easy code for understand how to use Transfromation for liveData

### public functions of Transfromation 

 - map : Applies the given function on the main thread to each value emitted by source LiveData and returns LiveData, which emits resulting values.
 
### Demo requirements :

 - A customer model such as User, have members : firstName, lastName, old, sex
 
 - A MutableLiveData<User> be mapped to a LiveData fullName equals firstName + " " + lastName
 
 - Show fullName on a textView
 
### code :

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

### references

 - https://developer.android.com/reference/android/arch/lifecycle/Transformations
 
 - https://github.com/benedictcontawe/JetpackComponentsApp/blob/Transformations_Map/app/src/main/java/com/example/jetpackcomponentsapp/MainViewModel.java
 