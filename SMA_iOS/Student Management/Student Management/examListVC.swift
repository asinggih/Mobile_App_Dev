//
//  examListVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 4/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

public class ExamCell: UITableViewCell{
    
    @IBOutlet weak var examTitle: UILabel!
    @IBOutlet weak var examDate: UILabel!
    @IBOutlet weak var examTime: UILabel!
    @IBOutlet weak var examLocation: UILabel!
    
    
}

class examListVC: UIViewController {

    @IBOutlet weak var examTableView: UITableView!
    
    var examList:[Exam] = []
    
    @IBOutlet weak var cancelButton: UIBarButtonItem!
    @IBOutlet weak var addExam: UIBarButtonItem!
    @IBOutlet weak var selectAll: UIBarButtonItem!
    @IBOutlet weak var deleteSel: UIButton!
    
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        toggleEdittingButtons()
        getExams()
        NotificationCenter.default.addObserver(self, selector: #selector(refreshTable), name: NSNotification.Name(rawValue: "reloadExam"), object: nil)
        
        examTableView.delegate = self
        examTableView.dataSource = self
        
        let longPressGesture:UILongPressGestureRecognizer = UILongPressGestureRecognizer(target: self, action: #selector(self.longPress(_:)))
        longPressGesture.minimumPressDuration = 1.0 // 1 second press
        examTableView.addGestureRecognizer(longPressGesture)
        examTableView.allowsMultipleSelectionDuringEditing = true
        

    }
    
    @objc func refreshTable(notification: NSNotification){
        getExams()
        self.examTableView.reloadData()
    }
    
    @objc func longPress(_ longPressGestureRecognizer: UILongPressGestureRecognizer) {
        
        if longPressGestureRecognizer.state == UIGestureRecognizerState.began {
            
            let touchPoint = longPressGestureRecognizer.location(in: self.examTableView)
            if examTableView.indexPathForRow(at: touchPoint) != nil {
                self.examTableView.isEditing = true
                toggleEdittingButtons()
                
            }
        }
    }
    @IBAction func cancel(_ sender: Any) {
        self.examTableView.isEditing = false
        toggleEdittingButtons()
    }
    
    @IBAction func selectAllExams(_ sender: Any) {
        for section in 0..<examTableView.numberOfSections {
            for row in 0..<examTableView.numberOfRows(inSection: section) {
                examTableView.selectRow(at: IndexPath(row: row, section: section), animated: false, scrollPosition: .none)
            }
        }
    }
    
    
    @IBAction func deleteSelection(_ sender: Any) {
        let selected_rows =  examTableView.indexPathsForSelectedRows ?? []
        
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
    
    func generateAlert(_ examsToBeDeleted:Int, _ toBeDeletedArray:[IndexPath]) -> UIAlertController{
        let numOfExams = String(examsToBeDeleted)
        let message = "You are about to delete \(numOfExams) exams"
        
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
            context.delete(examList[indexPath.row])
            self.examList.remove(at: indexPath.row)
            examTableView.deleteRows(at: [indexPath], with: .fade)
        }
        
        appDelegate.saveContext()
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func toggleEdittingButtons(){
        if self.examTableView.isEditing {
            cancelButton.title = "Cancel"
            selectAll.title = "Select all"
            addExam.title = ""
            deleteSel.isHidden = false
            
            
        }
        else{
            cancelButton.title = ""
            selectAll.title = ""
            addExam.title = "Add exam"
            deleteSel.isHidden = true
        }
        
    }
    
    private func getExams() {
        do{
            examList = try context.fetch(Exam.fetchRequest())
        }
        catch{
            examList = []
        }
    }
    
}


extension examListVC: UITableViewDelegate{
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
}

extension examListVC: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return examList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let examCell = tableView.dequeueReusableCell(withIdentifier: "examCell", for: indexPath) as! ExamCell
        
        let exam = examList[indexPath.row]
        
        examCell.examTitle.text = exam.name
        examCell.examTime.text = exam.time
        examCell.examLocation.text = exam.location
        
        return examCell
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        
        let appDelegate = (UIApplication.shared.delegate as! AppDelegate)
        
        if editingStyle == .delete{
            context.delete(examList[indexPath.row])  // delete from Core Data
            self.examList.remove(at: indexPath.row)  // delete from list
            examTableView.deleteRows(at: [indexPath], with: .fade) // delete from table
            
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








