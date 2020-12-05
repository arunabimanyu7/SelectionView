# SelectionView

[![](https://jitpack.io/v/arunabimanyu7/SelectionView.svg)](https://jitpack.io/#arunabimanyu7/SelectionView)

SelectionView is a utilty class inherited from radio group class.this class takes care of setting selection state of that view when particular item is clicked


To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
 Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.arunabimanyu7:SelectionView:-SNAPSHOT'
	}

 How do I use SelectionView?
    Simple use cases will look something like this:
    
      * Add your your any views inside selectionView class 
     <com.arun.selectionview.SelectionView
        android:id="@+id/selectionview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:orientation="vertical"
        android:tag="cash"
        bind:layout_constraintBottom_toBottomOf="parent"
        bind:layout_constraintEnd_toEndOf="parent"
        bind:layout_constraintStart_toStartOf="parent"
        bind:layout_constraintTop_toTopOf="parent"
        bind:selectedId="@id/card">


        <com.google.android.material.button.MaterialButton
            android:id="@+id/card"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="8dp"
            android:gravity="start"
            android:padding="8dp"
            android:tag="cash"
            android:text="Online payment"
            android:textColor="@color/white"
            android:textSize="14sp"
            bind:backgroundTint="@color/black"
            bind:cornerRadius="8dp"
            bind:icon="@drawable/payment_selector"
            android:background="@color/black"
            bind:iconPadding="16dp"
            bind:iconTint="@color/colorAccent"
            bind:strokeColor="@color/black" />


        <com.google.android.material.button.MaterialButton
            android:id="@+id/cash"
            style="@style/MaterialButtonStyleOutline"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="8dp"
            android:gravity="start"
            android:padding="8dp"
            android:tag="cash"
            android:text="Cash After Service"
            android:textColor="@color/white"
            android:textSize="14sp"
            bind:backgroundTint="@color/black"
            android:background="@color/black"
            bind:cornerRadius="8dp"
            bind:icon="@drawable/payment_selector"
            bind:iconPadding="16dp"
            bind:iconTint="@color/colorAccent"
            bind:strokeColor="@color/black" />

    </com.arun.selectionview.SelectionView>
    
   Add selector for your drawables
    
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/payment_unchecked" android:state_activated="false" />
    <item android:drawable="@drawable/payment_checked" android:state_activated="true" />
    <item android:drawable="@drawable/payment_unchecked" android:state_checked="false" />
    <item android:drawable="@drawable/payment_checked" android:state_checked="true" />
    </selector>
    
    
  * You can specify selected view to selection view
     by adding this attribte
     
        bind:selectedId="@id/id_of_your_view"
     
     or programatically select your view at run time
     
        selectionView.setSelectedView(yourView)
    
  * Add ItemSelectionListener to selection view class to get callbacks from action
    
          fun onItemSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)
          fun onItemReSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)
  
    
  Note : 
        Dont set onclick listener for your child view.you can get callbacks from ItemSelectionListener
    
  Compatibility
    
    Minimum Android SDK: SelectionView requires a minimum API level of 19.
    Compile Android SDK: SelectionView you to compile against API 29 or later.
    
    
    