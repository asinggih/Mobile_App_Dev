//
//  newStudentVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 30/9/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

class newStudentVC: UIViewController {

    @IBOutlet weak var requiredWarning: UILabel!
    
    @IBOutlet weak var firstNameInput: UITextField!
    @IBOutlet weak var lastNameInput: UITextField!
    @IBOutlet weak var dobInput: UITextField!
    @IBOutlet weak var addressInput: UITextField!
    
    @IBOutlet weak var genderSelection: UITextField!
    @IBOutlet weak var programSelection: UITextField!
    
    
    var datePicker: UIDatePicker = UIDatePicker()
    var genderPicker: UIPickerView = UIPickerView()     // tag = 1
    var programPicker: UIPickerView = UIPickerView()    // tag = 2
    
    var programList:[Program] = []
    var genderList:[String] = []
    
    var dateFormatter = DateFormatter()
    
    
    var dateStorage = Date()            // to hold proper date format for core data storage
    var programStorage = Program()
    
    var fn:String!
    var ln:String!
    var gender:String!
    var address:String!
    var prog:String!
    var dob:String!
    
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
        let backDate = calendar.date(byAdding: .year, value: -20, to: Date())
        
        datePicker.datePickerMode = .date
        datePicker.setDate(backDate!, animated: true)
        datePicker.addTarget(self, action: #selector(datePickerChanged(_:)), for: .valueChanged)    // run the datePickerChanged method
                                                                                                    // when value of datePicker is changed
        
        
        genderList = ["Male", "Female", "Other"]
        programList = getProgramList()
        
        
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
        // Dispose of any resources that can be recreated.
    }
    
    
    /* --------------------------------------------------------
                Snippets are from Apoorv Mote
     --------------------------------------------------------*/
    @objc func datePickerChanged(_ sender: UIDatePicker){
        dateStorage = sender.date
        dobInput.text = dateFormatter.string(from: sender.date)
        print(dateStorage)
    }
    
    // dismisses any editing when touched somewhere else
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }
    
    // ------------------- end snippet---------------------------

    @IBAction func cancel(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func submitButton(_ sender: Any) {
    
        fn = firstNameInput.text?.strip()
        ln = lastNameInput.text?.strip()
        address = addressInput.text?.strip()
        gender = genderSelection.text?.strip()
        dob = dobInput.text?.strip()
        prog = programSelection.text?.strip()
        
        if isValid(){
            processData(fn, ln, gender, dateStorage, address, programStorage)
            
            let confirmationMes = "\(fn!) \(ln!)"
            // creating UI alert
            alert = UIAlertController(title: "Successfully added", message: confirmationMes, preferredStyle: UIAlertControllerStyle.alert)
            
//            let OKAction = UIAlertAction(title: "OK", style: .default, handler: { _ -> Void in
//                let storyBoard = UIStoryboard(name: "Main", bundle: nil)
//                let nextViewController = storyBoard.instantiateViewController(withIdentifier: "home") as! ViewController
//                self.present(nextViewController, animated: true, completion: nil)
//            })
//
//            alert.addAction(OKAction)
//
            alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler:{ (action) -> Void in self.clearForm() }))

            self.present(alert, animated: true, completion: nil)
        
        }
        
    }
    
    private func clearForm() {
        firstNameInput.text = ""
        lastNameInput.text = ""
        addressInput.text = ""
        genderSelection.text = ""
        dobInput.text = ""
        programSelection.text = ""
    }
    
    private func isValid() -> Bool{
        
        if fn.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if ln.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if address.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if gender.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if prog.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        if dob.isEmpty{
            requiredWarning.isHidden = false
            return false
        }
        
        return true

    }
    private func processData(_ firstName: String,
                                 _ lastName: String,
                                 _ gender: String,
                                 _ dob: Date,
                                 _ address: String,
                                 _ program: Program) {
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
        let student = Student(context: context)
        
        student.firstName = firstName
        student.lastName = lastName
        student.address = address
        student.programs = programStorage
        student.gender = gender
        student.dob = dob
        
        appDelegate.saveContext()
    
    }
    
    private func getProgramList() -> [Program]{
        
        var list:[Program] = []
        do{
            list = try context.fetch(Program.fetchRequest())
        }
        catch{
            print("Fetching Error")
        }
        
        return list
    }
}


extension newStudentVC: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return genderList[row]
        }
        else{
            return programList[row].name
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
            programStorage = programList[row]
            programSelection.text = programList[row].name
        }
    }
    
    
}
