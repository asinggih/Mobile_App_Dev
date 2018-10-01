//
//  newStudentVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 30/9/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

class newStudentVC: UIViewController {

    @IBOutlet weak var dob: UITextField!
    @IBOutlet weak var genderSelection: UITextField!
    @IBOutlet weak var programSelection: UITextField!
    @IBOutlet weak var requiredWarning: UILabel!
    
    var datePicker: UIDatePicker = UIDatePicker()
    var genderPicker: UIPickerView = UIPickerView()     // tag = 1
    var programPicker: UIPickerView = UIPickerView()    // tag = 2
    
    var programList:[String] = []
    var genderList:[String] = []
    
    var dateFormatter = DateFormatter()
    
//    override var preferredStatusBarStyle : UIStatusBarStyle {
//        return UIStatusBarStyle.lightContent
//    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
       
        
        requiredWarning.isHidden = true
        
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
        
        
        genderList = ["Male", "Female", "Other"]
        
        programList = ["Master of ICT",
                        "Master of Data Science",
                        "Bachelor of Arts",
                        "Bachelor of Engineering",
                        "Bachelor of Science"]
        
        
        
        genderPicker.tag = 1
        genderPicker.dataSource = self
        genderPicker.delegate = self
        
        programPicker.tag = 2
        programPicker.dataSource = self
        programPicker.delegate = self
        
        
        
        // trigger respective picker views
        dob.inputView = datePicker
        genderSelection.inputView = genderPicker
        programSelection.inputView = programPicker
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    /* --------------------------------------------------------
                Snippets are from Apoorv Mote
     --------------------------------------------------------*/
    @objc func datePickerChanged(_ sender: UIDatePicker){
        dob.text = dateFormatter.string(from: sender.date)
    }
    
    // dismisses any editing when touched somewhere else
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }

    @IBAction func cancel(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
}

extension newStudentVC: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return genderList[row]
        }
        else{
            return programList[row]
        }
    }
    
}

extension newStudentVC: UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1{
            return genderList.count
        }
        else{
            return programList.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1 {
            genderSelection.text = genderList[row]
        }
        else{
            programSelection.text = programList[row]
        }
    }
    
    
}
