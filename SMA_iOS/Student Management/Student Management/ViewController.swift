//
//  ViewController.swift
//  Student Management
//
//  Created by Aditya Singgih on 30/9/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit


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
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Adding observer(a listener) to wait for studentList reload method
        NotificationCenter.default.addObserver(self, selector: #selector(refreshTable), name: NSNotification.Name(rawValue: "load"), object: nil)
        
//        let appDelegate = UIApplication.shared.delegate as! AppDelegate

        /* -------------------------------------------------
                        Preload Study Programs
        -------------------------------------------------*/
        
//        let programList = ["Master of ICT",
//                       "Master of Data Science",
//                       "Bachelor of Arts",
//                       "Bachelor of Engineering",
//                       "Bachelor of Science"]
//
//        for item in programList {
//            let program = Program(context: context)
//            program.name = item
//        }
//
//        appDelegate.saveContext()
//        
        do{
            studentList = try context.fetch(Student.fetchRequest())
        }
        catch{
            studentList = []
        }
        
        // tableView actions setup
        let longPressGesture:UILongPressGestureRecognizer = UILongPressGestureRecognizer(target: self, action: #selector(self.longPress(_:)))
        longPressGesture.minimumPressDuration = 1.0 // 1 second press
        studentsTableView.addGestureRecognizer(longPressGesture)
        self.studentsTableView.allowsMultipleSelectionDuringEditing = true
        
        studentsTableView.delegate = self
        studentsTableView.dataSource = self
        
        
    }
    
    @objc func longPress(_ longPressGestureRecognizer: UILongPressGestureRecognizer) {
        
        if longPressGestureRecognizer.state == UIGestureRecognizerState.began {
            
            let touchPoint = longPressGestureRecognizer.location(in: self.view)
            if let indexPath = studentsTableView.indexPathForRow(at: touchPoint) {
                print(indexPath)
                self.studentsTableView.isEditing = true
            }
        }
    }
    
    @objc func refreshTable(notification: NSNotification){
        do{
            studentList = try context.fetch(Student.fetchRequest())
        }
        catch{
            studentList = []
        }
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
    
//    private func toggleEdittingButtons() {
//        if self.studentsTableView.isEditing{
//
//        }
//        else{
//
//        }
//    }
//
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
        studentCell.sID.text = "1234"
        studentCell.sProgram.text = student.programs?.name
        return studentCell
    }
    
    
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete{
            self.studentList.remove(at: indexPath.row)
            studentsTableView.deleteRows(at: [indexPath], with: .fade)
        }
    }
    


}








