//
//  ViewController.swift
//  Student Management
//
//  Created by Aditya Singgih on 30/9/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit
import CoreData

public class StudentCell: UITableViewCell {
    
    @IBOutlet weak var sPic: UIImageView!
    @IBOutlet weak var sName: UILabel!
    @IBOutlet weak var sID: UILabel!
    @IBOutlet weak var sProgram: UILabel!
    
}


class ViewController: UIViewController {
    
    var programs:[Program] = []
    var studentList:[Student] = []
    
    @IBOutlet weak var studentsTableView: UITableView!
//    @IBOutlet weak var studentCell: StudentCell!
    
    // Table editing related buttons
    @IBOutlet weak var cancelEdit: UIBarButtonItem!
    @IBOutlet weak var addStudent: UIBarButtonItem!
    @IBOutlet weak var delSelection: UIButton!
    @IBOutlet weak var selectAll: UIBarButtonItem!          // need to set its text
    
    @IBOutlet weak var examContoller: UIBarButtonItem!
    @IBOutlet weak var studentController: UIBarButtonItem!
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    var studentToBePassed:Student?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        /* -----------------------------------
            Preloading Study programs.
            Uncomment below if this is first time running on the current sim
        --------------------------------------*/
//        preloadProgramsData()
        
        toggleEdittingButtons()

        // Adding observer(a listener) to wait for triggering studentList reload method
        NotificationCenter.default.addObserver(self, selector: #selector(refreshTable), name: NSNotification.Name(rawValue: "load"), object: nil)

        getStudents()
        
        // tableView actions setup
        let longPressGesture:UILongPressGestureRecognizer = UILongPressGestureRecognizer(target: self, action: #selector(self.longPress(_:)))
        longPressGesture.minimumPressDuration = 1.0 // 1 second press
        studentsTableView.addGestureRecognizer(longPressGesture)
        studentsTableView.allowsMultipleSelectionDuringEditing = true
        
        studentsTableView.delegate = self
        studentsTableView.dataSource = self
        
    }
    
    @objc func longPress(_ longPressGestureRecognizer: UILongPressGestureRecognizer) {
        
        if longPressGestureRecognizer.state == UIGestureRecognizerState.began {
            
            let touchPoint = longPressGestureRecognizer.location(in: self.studentsTableView)
            if studentsTableView.indexPathForRow(at: touchPoint) != nil {
                self.studentsTableView.isEditing = true
                toggleEdittingButtons()
                
            }
        }
    }
    
    @objc func refreshTable(notification: NSNotification){
        getStudents()
        self.studentsTableView.reloadData()
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        
        if self.studentsTableView.isEditing {
            if identifier == "editStuSegue"{
                return false
            }
        }
        
        return true
    }
    
    private func toggleEdittingButtons() {
        
        let animationDuration:CFTimeInterval = 0.15
        animateNavigationChanges(animationDuration)
        delSelection.fadeTransition(animationDuration)
        
        if self.studentsTableView.isEditing == true{
            cancelEdit.title = "Cancel"
            selectAll.title = "Select all"
            addStudent.title = ""
            delSelection.isHidden = false
            studentController.isEnabled = false
            examContoller.isEnabled = false

        }
        else{
            cancelEdit.title = ""
            selectAll.title = ""
            addStudent.title = "Add student"
            delSelection.isHidden = true
            studentController.isEnabled = true
            examContoller.isEnabled = true
        }
    }
    
    private func getStudents() {
        do{
            studentList = try context.fetch(Student.fetchRequest())
        }
        catch{
            studentList = []
        }
    }
    
    private func clearStudentsList() {
        
        let fetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Student")
        let delRequest = NSBatchDeleteRequest(fetchRequest: fetch)
        
        do {
            try context.execute(delRequest)
            try context.save()
        } catch {
            print ("There was an error")
        }

    }
    
    private func preloadProgramsData() {
        let appDelegate = (UIApplication.shared.delegate as! AppDelegate)
        let programList = ["Master of ICT",
                           "Master of Data Science",
                           "Bachelor of Arts",
                           "Bachelor of Engineering",
                           "Bachelor of Science"]
        
        for item in programList {
            let program = Program(context: context)
            program.name = item
        }
        appDelegate.saveContext()
    }

    @IBAction func cancelEditAction(_ sender: Any) {
        self.studentsTableView.isEditing = false
        toggleEdittingButtons()
    }
    
    // Select all button function.
    @IBAction func selectAllStudents(_ sender: Any) {
        for section in 0..<studentsTableView.numberOfSections {
            for row in 0..<studentsTableView.numberOfRows(inSection: section) {
                studentsTableView.selectRow(at: IndexPath(row: row, section: section), animated: false, scrollPosition: .none)
            }
        }
    }
    @IBAction func deleteSelection(_ sender: Any) {
        let selected_rows =  studentsTableView.indexPathsForSelectedRows ?? []
        
        if selected_rows.count > 0{
            /* NOTE:
             Array needs to be reversed so that it deletes
             the item with higher index first. Otherwise the list
             will go out of range since the index gets smaller and smaller
             with the removal
            */
            let alert = generateAlert(selected_rows.count, selected_rows)
            
            self.present(alert, animated: true, completion: nil)
            
        }
        
    }
    
    func generateAlert(_ studentsToBeDeleted:Int, _ toBeDeletedArray:[IndexPath]) -> UIAlertController{
        let numOfStudents = String(studentsToBeDeleted)
        let message = "You are about to delete \(numOfStudents) sad students"
        
        let alert = UIAlertController(title: "Are you sure ?",
                                      message: message,
                                      preferredStyle: UIAlertControllerStyle.alert)
        
        let okButton = UIAlertAction(title: "Yes",
                                     style: UIAlertActionStyle.default,
                                     handler: {action in self.comboDelete(toBeDeletedArray)})
        
        let cancelButton = UIAlertAction(title: "No",
                                         style: UIAlertActionStyle.default,
                                         handler: nil)
        
        alert.addAction(cancelButton)
        alert.addAction(okButton)
        
        return alert
    }
    
    func comboDelete(_ itemsToBeDeleted:[IndexPath]) {
        
        let appDelegate = (UIApplication.shared.delegate as! AppDelegate)
        
        for indexPath in itemsToBeDeleted.reversed(){
            context.delete(studentList[indexPath.row])
            self.studentList.remove(at: indexPath.row)
            studentsTableView.deleteRows(at: [indexPath], with: .fade)
        }
        
        appDelegate.saveContext()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {

        if segue.identifier == "editStuSegue" {
            let editStuVC = segue.destination as! editStudentVC
//            editStuVC.student = studentToBePassed
            editStuVC.student = studentList[(studentsTableView.indexPathForSelectedRow?.row)!]
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func animateNavigationChanges(_ duration:CFTimeInterval) {
        let fadeTextAnimation = CATransition()
        fadeTextAnimation.duration = duration
        fadeTextAnimation.type = kCATransitionFade
        
        navigationController?.navigationBar.layer.add(fadeTextAnimation, forKey: "fadeText")
    }

}

extension ViewController: UITableViewDelegate{
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
}

extension ViewController: UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return studentList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        // init the cell
        let studentCell = tableView.dequeueReusableCell(withIdentifier: "studentCell", for: indexPath) as! StudentCell
        
        let student = studentList[indexPath.row]
        
        studentCell.sName.text = "\(student.firstName!) \(student.lastName!)"
        studentCell.sID.text = "\(student.studentID!)"
        studentCell.sProgram.text = student.programs?.name
        return studentCell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        studentToBePassed = studentList[indexPath.row]
        if !studentsTableView.isEditing{
            studentsTableView.deselectRow(at: indexPath, animated: true)
        }
    }
    
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        
        let appDelegate = (UIApplication.shared.delegate as! AppDelegate)
        
        if editingStyle == .delete{
            context.delete(studentList[indexPath.row])  // delete from Core Data
            self.studentList.remove(at: indexPath.row)  // delete from list
            studentsTableView.deleteRows(at: [indexPath], with: .fade) // delete from table
            
            appDelegate.saveContext() // commit the deletion 
            
        }
    }
    


}

extension UIView {
    func fadeTransition(_ duration:CFTimeInterval) {
        let animation = CATransition()
        animation.timingFunction = CAMediaTimingFunction(name:
            kCAMediaTimingFunctionEaseInEaseOut)
        animation.type = kCATransitionFade
        animation.duration = duration
        layer.add(animation, forKey: kCATransitionFade)
    }
}




