//
//  editStudentVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 2/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

class editStudentVC: UIViewController {

    @IBOutlet weak var scrollView: UIScrollView!
    
    
    @IBOutlet weak var requiredWarning: UILabel!
    
    @IBOutlet weak var studentID: UILabel!
    @IBOutlet weak var firstNameInput: UITextField!
    @IBOutlet weak var lastNameInput: UITextField!
    @IBOutlet weak var genderSelection: UITextField!
    
    
    @IBOutlet weak var dobInput: UITextField!
    @IBOutlet weak var addressInput: UITextField!
    
    
    @IBOutlet weak var programSelection: UITextField!
    
    var datePicker: UIDatePicker = UIDatePicker()
    var genderPicker: UIPickerView = UIPickerView()     // tag = 1
    var programPicker: UIPickerView = UIPickerView()    // tag = 2
    
    var genderList:[String] = []
    var programList:[String] = []
    
    var dateFormatter = DateFormatter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let recognizer = UITapGestureRecognizer(target: self, action: #selector(touch))
        recognizer.numberOfTapsRequired = 1
        recognizer.numberOfTouchesRequired = 1
        scrollView.addGestureRecognizer(recognizer)
        
        requiredWarning.isHidden = true
        
        genderList = ["Male", "Female", "Other"]
        
        programList = ["Master of ICT",
                       "Master of Data Science",
                       "Bachelor of Arts",
                       "Bachelor of Engineering",
                       "Bachelor of Science"]
        
        // Make the date format pretty for input
        dateFormatter.dateStyle = .medium
        dateFormatter.timeStyle = .none
        
        // offsetting default date from current year
        let calendar = Calendar.current
        let backDate = calendar.date(byAdding: .year, value: -20, to: Date())
        
        datePicker.datePickerMode = .date
        datePicker.setDate(backDate!, animated: true)
        datePicker.addTarget(self, action: #selector(datePickerChanged(_:)), for: .valueChanged)    // run the datePickerChanged method
        // when value of datePicker is changed
        
        genderPicker.tag = 1
        genderPicker.dataSource = self
        genderPicker.delegate = self
        
        programPicker.tag = 2
        programPicker.dataSource = self
        programPicker.delegate = self
        
        
        
        // trigger respective picker views
        dobInput.inputView = datePicker
        genderSelection.inputView = genderPicker
        programSelection.inputView = programPicker
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    /* --------------------------------------------------------
                Snippets are from Apoorv Mote
     --------------------------------------------------------*/
    @objc func datePickerChanged(_ sender: UIDatePicker){
        dobInput.text = dateFormatter.string(from: sender.date)
    }
    
//    // dismisses any editing when touched somewhere else
//    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
//        view.endEditing(true)
//    }
    
    // ------------------- end snippet---------------------------
    
    @objc func touch() {
        self.view.endEditing(true)
    }
}

extension editStudentVC: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return genderList[row]
        }
        return programList[row]
        
    }
}

extension editStudentVC: UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1{
            return genderList.count
        }
        return programList.count
    }
    
    
}












