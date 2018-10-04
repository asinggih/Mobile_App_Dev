//
//  newExamVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 5/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

class newExamVC: UIViewController {

    @IBOutlet weak var requiredWarning: UILabel!
    @IBOutlet weak var examNameInput: UITextField!
    @IBOutlet weak var examDateInput: UITextField!
    @IBOutlet weak var examDateTimeInput: UITextField!
    @IBOutlet weak var examLocationInput: UITextField!
    
    var datePicker: UIDatePicker = UIDatePicker()
    var timePicker: UIPickerView = UIPickerView()
    
    var timeList:[String] = []
    
    var dateFormatter = DateFormatter()
    var dateStorage = Date()
    
    var en:String!
    var ed:String!
    var et:String!
    var el:String!
    
    var alert:UIAlertController!
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        requiredWarning.isHidden = true
        
        // Make the date format pretty for input
        dateFormatter.dateStyle = .medium
        dateFormatter.timeStyle = .none
        
        // offsetting default date from current year
        let calendar = Calendar.current
        let currentDate = calendar.date(byAdding: .year, value: 0, to: Date())
        
        datePicker.datePickerMode = .date
        datePicker.setDate(currentDate!, animated: true)
        datePicker.addTarget(self, action: #selector(datePickerChanged(_:)), for: .valueChanged)
        
        timeList = ["10:00", "13:00", "16:00"]
        
        timePicker.dataSource = self
        timePicker.delegate = self
        
        examDateInput.inputView = datePicker
        examDateTimeInput.inputView = timePicker
    }
    
    /* --------------------------------------------------------
                Snippets are from Apoorv Mote
     --------------------------------------------------------*/
    @objc func datePickerChanged(_ sender: UIDatePicker){
        dateStorage = sender.date
        examDateInput.text = dateFormatter.string(from: sender.date)
        print(dateStorage)
    }
    
    // dismisses any editing when touched somewhere else
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }
    
    // ------------------- end snippet---------------------------

    @IBAction func cancel(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func submit(_ sender: Any) {
        en = examNameInput.text?.strip()
        ed = examDateInput.text?.strip()
        et = examDateTimeInput.text?.strip()
        el = examLocationInput.text?.strip()
        
        if isValid(){
            processData(en, dateStorage, et, el)
            
            // creating UI alert
            alert = UIAlertController(title: "Successfully added", message: en, preferredStyle: UIAlertControllerStyle.alert)
            
            alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler:{ (action) -> Void in self.clearForm() }))
            
            self.present(alert, animated: true, completion: nil)
            
            // let the observer know that we should update the studentTable datasource
            NotificationCenter.default.post(name: NSNotification.Name(rawValue: "reloadExam"), object: nil)
            
        }
    }
    
    private func clearForm() {
        examNameInput.text = ""
        examDateInput.text = ""
        examDateTimeInput.text = ""
        examLocationInput.text = ""
    }

    private func isValid() -> Bool{
        
        if en.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if ed.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if et.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if el.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        return true
        
    }
    
    private func processData(_ examName: String,
                             _ examDate: Date,
                             _ examTime: String,
                             _ examLoc: String) {
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let exam = Exam(context: context)
        
        exam.name = examName
        exam.date = examDate
        exam.time = examTime
        exam.location = examLoc
        
        appDelegate.saveContext()
        
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


}

extension newExamVC: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return timeList[row]
    }
}

extension newExamVC: UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return timeList.count
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        examDateTimeInput.text = timeList[row]
    }
    
    
}
















