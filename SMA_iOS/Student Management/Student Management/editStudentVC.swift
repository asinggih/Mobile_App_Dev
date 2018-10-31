//
//  editStudentVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 2/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

class StudentExamCell: UITableViewCell{
    
    @IBOutlet weak var examTitle: UILabel!
    @IBOutlet weak var examDate: UILabel!
    @IBOutlet weak var examTime: UILabel!
    @IBOutlet weak var examLocation: UILabel!
    
}

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
    @IBOutlet weak var addExamBut: UIButton!
    
    
    
    var datePicker: UIDatePicker = UIDatePicker()
    var genderPicker: UIPickerView = UIPickerView()     // tag = 1
    var programPicker: UIPickerView = UIPickerView()    // tag = 2
    
    var genderList:[String] = []
    var programList:[Program] = []
    
    var dateFormatter = DateFormatter()

    var student:Student!
    var programStorage = Program()
    var examList:[Exam] = []
    
    var fn:String!
    var ln:String!
    var gender:String!
    var address:String!
    var prog:String!
    
    var alert:UIAlertController!
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        /*
         handle scrollview and picker touch selection.
         Without this below, we can't touch to exit picker view
        */
        
        let recognizer = UITapGestureRecognizer(target: self, action: #selector(touch))
        recognizer.numberOfTapsRequired = 1
        recognizer.numberOfTouchesRequired = 1
        scrollView.addGestureRecognizer(recognizer)
        
        requiredWarning.isHidden = true
        
        genderList = ["Male", "Female", "Other"]
        
        programList = getProgramList()
        
        // Make the date format pretty for input
        dateFormatter.dateStyle = .medium
        dateFormatter.timeStyle = .none
        
        /*
         Populate data of student from our db
         */
        studentID.text = student.studentID
        firstNameInput.text = student.firstName
        lastNameInput.text = student.lastName
        genderSelection.text = student.gender
        dobInput.text = dateFormatter.string(for: student.dob!)
        addressInput.text = student.address
        programSelection.text = student.programs?.name
        programStorage = student.programs!
        
        print(student.dob!)
        
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
        
        addExamBut.setTitle("\u{2b}", for: .normal) 
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @objc func touch() {
        self.view.endEditing(true)
    }
    
    @IBAction func addExamButton(_ sender: Any) {
        print("hello")
    }
    
    @IBAction func saveButton(_ sender: Any) {
        
        fn = firstNameInput.text?.strip()
        ln = lastNameInput.text?.strip()
        address = addressInput.text?.strip()
        gender = genderSelection.text?.strip()
        prog = programSelection.text?.strip()
        
        if isValid() {
            
            processData(fn, ln, gender, address, programStorage, examList)
            NotificationCenter.default.post(name: NSNotification.Name(rawValue: "load"), object: nil)
            
            let confirmationMes = "\(fn!) \(ln!)"
            alert = UIAlertController(title: "Successfully updated", message: confirmationMes, preferredStyle: UIAlertControllerStyle.alert)
            
            alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
            
            
        }
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
        
        return true
        
    }
    
    private func processData(_ firstName: String,
                             _ lastName: String,
                             _ gender: String,
                             _ address: String,
                             _ program: Program,
                             _ examList:[Exam]) {
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        student.firstName = firstName
        student.lastName = lastName
        student.address = address
        student.programs = programStorage
        student.gender = gender
        
        if examList.count > 0 {
            for exam in examList{
                student.addToExams(exam)
            }
        }
        
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

extension editStudentVC: UIPickerViewDelegate{
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return genderList[row]
        }
        return programList[row].name
        
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
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1{
            genderSelection.text = genderList[row]
        }
        else{
            programStorage = programList[row]
            programSelection.text = programList[row].name
        }
    }
    
}

extension editStudentVC: UITableViewDelegate{
    
}

extension editStudentVC: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return examList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let studentExamCell = tableView.dequeueReusableCell(withIdentifier: "studentExamCell", for: indexPath) as! ExamCell
        
        let exam = examList[indexPath.row]
        
        studentExamCell.examTitle.text = exam.name
        studentExamCell.examTime.text = exam.time
        studentExamCell.examLocation.text = exam.location
        
        return studentExamCell
    }
    
    
}











